package ita.tw.afs.spark.service;


import ita.tw.afs.spark.Util.SparkUtil;
import ita.tw.afs.spark.dto.GeneralResponse;
import ita.tw.afs.spark.dto.OrdersResponse;
import ita.tw.afs.spark.exception.GeneralException;
import ita.tw.afs.spark.mapper.OrdersMapper;
import ita.tw.afs.spark.model.Orders;
import ita.tw.afs.spark.model.ParkingLot;
import ita.tw.afs.spark.model.Reservation;
import ita.tw.afs.spark.repository.OrdersRepository;
import ita.tw.afs.spark.repository.ParkingLotRepository;
import ita.tw.afs.spark.repository.ReservationRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class OrdersService {
    private static final String OBJECT_NOT_FOUND = "OBJECT_NOT_FOUND";
    private static final String PARKING_LOT_NOT_FOUND = "Parking Lot not found";
    private static final String PLEASE_INPUT_ALL_REQUIRED_FIELDS = "PLEASE INPUT ALL REQUIRED FIELDS";
    private static final String ORDER_ALREADY_CLOSED = "ORDER ALREADY CLOSED";
    private static final String CLOSED = "CLOSED";
    private static final int BONUS_HOUR = 1;
    private static final String OPEN = "OPEN";
    public static final String CONFIRMED = "CONFIRMED";
    public static final String N_A = "N/A";

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    ParkingBlockService parkingBlockService;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ParkingLotService parkingLotService;

    @Autowired
    ParkingLotRepository parkingLotRepository;

    public Orders saveIfHasAvailableParkingBlocks(Orders order, Long parkingBoyId) throws NotFoundException {
        if (order.getParkingLotId() != null
                && !order.getPlateNumber().isEmpty()
                && order.getParkingBlockPosition() != null) {
            Optional<ParkingLot> parkingLot = parkingLotRepository.findById(order.getParkingLotId());
            if (parkingLot.isPresent()) {
                Boolean isParkingBlockValid = parkingLotService.checkIfParkingBlockPositionIsValid(order.getParkingLotId(),
                        order.getParkingBlockPosition(), order.getReservation());
                if(isParkingBlockValid){
                    if(order.getReservation().isPresent()){
                        Long reservationNumber = order.getReservation().get().getReservationNumber();
                        Reservation reservation = reservationRepository.findOneByReservationNumber(reservationNumber);

                        reservation.setStatus(CONFIRMED);
                        reservationRepository.save(reservation);
                        order.setReservation(reservation);
                    }
                }
                return saveOrderAndUpdateParkingBlockStatus(order, parkingBoyId);
            }
            throw new NotFoundException(PARKING_LOT_NOT_FOUND);
        }
        throw new NotFoundException(PLEASE_INPUT_ALL_REQUIRED_FIELDS);
    }

    public Orders saveOrderAndUpdateParkingBlockStatus(Orders orders, Long parkingBoyId) throws NotFoundException {
        orders.setStatus(OPEN);
        orders.setTimeIn(getCurrentDateTime());
        orders.setCreatedBy(parkingBoyId);

        parkingBlockService.updateParkingBlockStatusToOccupied(orders);
        return ordersRepository.save(orders);
    }

    public List<OrdersResponse> getOrders() {
        OrdersMapper mapper = new OrdersMapper();
        List<OrdersResponse> mappedResponse = new ArrayList<>();
        ordersRepository.findByStatus("OPEN").forEach(order -> {
            HashMap<String, Object> keyValue = new HashMap<>();
            keyValue.put("Order", order);
            Optional<ParkingLot> parkingLot = parkingLotRepository.findById(order.getParkingLotId());
            keyValue.put("ParkingLot", parkingLot.get());
            mappedResponse.add(mapper.mappedResponse(keyValue));
        });

        return mappedResponse;
    }

    public Optional<Orders> getOrderById(Long orderId) throws NotFoundException {
        Optional<Orders> ordersOptional = ordersRepository.findById(orderId);
        if (ordersOptional.isPresent()) {
            if(!ordersOptional.get().getStatus().equals(CLOSED)){
                Double price = computeOrderPrice(ordersOptional.get().getTimeIn(),
                        getCurrentDateTime(), ordersOptional.get().getParkingLotId());
                ordersOptional.get().setTimeOut(N_A);
                ordersOptional.get().setPrice(price);
            }
            return ordersOptional;
        }

        throw new NotFoundException(OBJECT_NOT_FOUND);
    }

    public GeneralResponse closeOrderById(Long parkingBoyId, Orders orderId) throws GeneralException,  NotFoundException  {
        try {
            Optional<Orders> orders = ordersRepository.findByParkingBlockPositionAndParkingLotIdAndStatus(orderId.getParkingBlockPosition(), orderId.getParkingLotId(), "OPEN");
            if(orders.isPresent()){
                if(orders.get().getTimeOut() == null ||
                        orders.get().getTimeOut().isEmpty()){
                    String timeIn = orders.get().getTimeIn();
                    String timeOut = getCurrentDateTime();
                    Long parkingLotId = orders.get().getParkingLotId();
                    Integer parkingBlockPosition = orders.get().getParkingBlockPosition();
                    orders.get().setStatus(CLOSED);
                    orders.get().setTimeOut(timeOut);
                    orders.get().setClosedBy(parkingBoyId);
                    orders.get().setPrice(computeOrderPrice(timeIn, timeOut, parkingLotId));
                    parkingBlockService.updateParkingBlockStatusToAvailable(parkingLotId, parkingBlockPosition);
                    return SparkUtil.setGeneralResponseOk("Success closing orders!", null);
                }
                throw new NotFoundException(ORDER_ALREADY_CLOSED);
            }
            throw new NotFoundException(OBJECT_NOT_FOUND);
        }
        catch (Exception e){
            throw new GeneralException("Something went wrong in the backend");
        }
    }

    private Double computeOrderPrice(String timeIn, String timeOut, Long parkingLotId) {
        Double parkingLotRate = parkingLotRepository.findById(parkingLotId).get().getRate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime parsedTimeIn = LocalDateTime.parse(timeIn, formatter);
        LocalDateTime parsedTimeOut = LocalDateTime.parse(timeOut, formatter);
        Duration duration = Duration.between(parsedTimeIn, parsedTimeOut);
        long renderedHours = Math.abs(duration.toHours());
        long checkExceedingMinutes = Math.abs(duration.toMinutes()) % 60;
        long finalComputedHours = renderedHours;
        if (checkExceedingMinutes != 0)
            finalComputedHours++;

        if (finalComputedHours > 0)
            finalComputedHours -= BONUS_HOUR;

        return finalComputedHours * parkingLotRate;
    }

    private String getCurrentDateTime() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return myDateObj.format(myFormatObj);
    }

    public Optional<Orders> getOrderByParkingLotIdAndParkingBlockPositionAndStatus(Orders orders) {
        return ordersRepository.findByParkingBlockPositionAndParkingLotIdAndStatus(orders.getParkingBlockPosition(), orders.getParkingLotId(), "OPEN");
    }

    public List<Orders> getCreatedOrdersByParkingBoyId(Long parkingBoyId) {
        return ordersRepository.getOrdersByCreatedBy(parkingBoyId);
    }

    public List<Orders> getClosedOrdersByParkingBoyId(Long parkingBoyId) {
        return ordersRepository.getOrdersByClosedBy(parkingBoyId);
    }
}
