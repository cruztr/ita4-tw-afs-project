package ita.tw.afs.spark.service;


import ita.tw.afs.spark.model.Orders;
import ita.tw.afs.spark.model.ParkingBlock;
import ita.tw.afs.spark.model.ParkingLot;
import ita.tw.afs.spark.repository.OrdersRepository;
import ita.tw.afs.spark.repository.ParkingLotRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class OrdersService {
    private static final String OBJECT_NOT_FOUND = "OBJECT_NOT_FOUND";
    private static final String PARKING_LOT_NOT_FOUND = "Parking Lot not found";
    private static final String PLEASE_INPUT_ALL_REQUIRED_FIELDS = "PLEASE INPUT ALL REQUIRED FIELDS";

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    ParkingBlockService parkingBlockService;

    @Autowired
    ParkingLotService parkingLotService;

    @Autowired
    ParkingLotRepository parkingLotRepository;

    public Orders saveIfHasAvailableParkingBlocks(Orders order, Long parkingBoyId) throws NotFoundException, NotSupportedException {
        if (order.getParkingLotId() != null
                && !order.getPlateNumber().isEmpty()
                && order.getParkingBlockPosition() != null) {
            Optional<ParkingLot> parkingLot = parkingLotRepository.findById(order.getParkingLotId());
            if (parkingLot.isPresent()) {
                ParkingBlock parkingBlock = parkingLotService.
                        checkIfParkingBlockPositionIsValid(order.getParkingLotId(),
                        order.getParkingBlockPosition());
                return saveOrderAndUpdateParkingBlockStatus(order, parkingBoyId);
            }
            throw new NotFoundException(PARKING_LOT_NOT_FOUND);
        }
        throw new NotFoundException(PLEASE_INPUT_ALL_REQUIRED_FIELDS);
    }

    public Orders saveOrderAndUpdateParkingBlockStatus(Orders orders, Long parkingBoyId) throws NotFoundException {
        orders.setStatus("OPEN");
        orders.setTimeIn(getCurrentDateTime());
        orders.setCreatedBy(parkingBoyId);

        parkingBlockService.updateParkingBlockStatus(orders);
        return ordersRepository.save(orders);
    }

    public Iterable<Orders> getOrdersByPage(Long createdBy) {
        return ordersRepository.findAllByCreatedBy(createdBy);
    }

    public Optional<Orders> getOrderByIdAndParkingNumber(Long createdBy, Long orderId) throws NotFoundException {
        Optional<Orders> checkIfOrderExists = ordersRepository.findByOrderIdAndCreatedBy(orderId, createdBy);

        if (checkIfOrderExists.isPresent()) {
            return checkIfOrderExists;
        }

        throw new NotFoundException(OBJECT_NOT_FOUND);
    }

    private String getCurrentDateTime() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return myDateObj.format(myFormatObj);
    }
}
