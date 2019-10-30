package ita.tw.afs.spark.service;

import ita.tw.afs.spark.dto.MyReservationResponse;
import ita.tw.afs.spark.dto.ReservationResponse;
import ita.tw.afs.spark.exception.ExistingCredentialException;
import ita.tw.afs.spark.exception.InvalidCredentialsException;
import ita.tw.afs.spark.mapper.MyReservationMapper;
import ita.tw.afs.spark.mapper.ReservationMapper;
import ita.tw.afs.spark.model.CarOwner;
import ita.tw.afs.spark.model.ParkingBlock;
import ita.tw.afs.spark.model.ParkingLot;
import ita.tw.afs.spark.model.Reservation;
import ita.tw.afs.spark.repository.CarOwnerRepository;
import ita.tw.afs.spark.repository.ReservationRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CarOwnerService {

    private static final String RESERVED = "RESERVED";
    private static final String AVAILABLE = "AVAILABLE";
    private static final String CANCELLED = "CANCELLED";
    private static final String USERNAME_IS_EXISTING = "Username is existing";
    private static final String INCORRECT_USERNAME_PASSWORD = "Incorrect username/password.";
    private static final String PLEASE_INPUT_ALL_REQUIRED_FIELDS = "Please input all required fields";
    private static final String PASSWORD_MUST_BE_AT_LEAST_8_CHARACTERS = "Password must be at least 8 characters.";

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CarOwnerRepository carOwnerRepository;

    @Autowired
    private ParkingLotService parkingLotService;

    @Autowired
    private ParkingBlockService parkingBlockService;

    public Reservation createReservation(Reservation reservation, Long parkingLotId)
            throws NotFoundException {


        Optional<ParkingLot> parkingLotOptional = parkingLotService.getParkingLot(parkingLotId);
        reservation.setStatus(RESERVED);
        reservation.setApplicationTime(getCurrentDateTime());
        reservation.setParkingLotId(parkingLotId);

        List<ParkingBlock> parkingBlockList = parkingLotOptional.get().getParkingBlocks();
        Integer parkingBlockPosition = getLastAvailableParkingBlock(parkingBlockList);
        reservation.setPosition(parkingBlockPosition);
        parkingBlockService.updateParkingBlockStatusToReserved(parkingLotId, parkingBlockPosition);

        return reservationRepository.save(reservation);

    }

    public Optional<Reservation> cancelReservation(Long reservationId) throws NotFoundException {
        Optional<Reservation> myReservation = reservationRepository.findById(reservationId);
        if(myReservation.isPresent()) {
            ParkingBlock reservedParkingBlock = parkingBlockService.findByParkingLotIdAndPositionAndStatus(myReservation.get().getParkingLotId(), myReservation.get().getPosition(), "RESERVED");

            if (reservedParkingBlock.getStatus().contains(RESERVED) && myReservation.get().getStatus().contains(RESERVED)) {
                reservedParkingBlock.setStatus(AVAILABLE);
                parkingBlockService.save(reservedParkingBlock);
                myReservation.get().setStatus(CANCELLED);
                reservationRepository.save(myReservation.get());
            }
            return myReservation;
        }
        throw new NotFoundException("Reservation not found");
    }

    private Integer getLastAvailableParkingBlock(List<ParkingBlock> parkingBlockList) {
        return parkingBlockList.stream()
                .filter(parkingBlock -> (parkingBlock.getStatus().equals(AVAILABLE)))
                .map(parkingBlock -> parkingBlock.getPosition()).sorted()
                .reduce((x,y) -> y).orElse(0);
    }

    public CarOwner login(String username, String password) throws InvalidCredentialsException {
        CarOwner carOwner= carOwnerRepository.findByUsernameAndPassword(username, password);
        if(Objects.nonNull(carOwner)){
            return carOwner;
        }
        throw new InvalidCredentialsException(INCORRECT_USERNAME_PASSWORD);
    }

    public CarOwner signUp(CarOwner carOwner) throws ExistingCredentialException, NotFoundException {
        if (!carOwner.getUsername().isEmpty() && !carOwner.getPassword().isEmpty()
            && !carOwner.getPlateNumber().isEmpty() && !carOwner.getFirstName().isEmpty()
            && !carOwner.getLastName().isEmpty()) {
            if(carOwner.getPassword().length() >= 8) {
                CarOwner fetchedCarOwner = carOwnerRepository.findByUsername(carOwner.getUsername());
                if (Objects.isNull(fetchedCarOwner)) {
                    return carOwnerRepository.save(carOwner);
                }
                throw new ExistingCredentialException(USERNAME_IS_EXISTING);
            }
            throw new NotFoundException(PASSWORD_MUST_BE_AT_LEAST_8_CHARACTERS);
        }
        throw new NotFoundException(PLEASE_INPUT_ALL_REQUIRED_FIELDS);
    }

    private String getCurrentDateTime() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return myDateObj.format(myFormatObj);
    }

    public MyReservationResponse getReservation(Long carOwnerId) throws NotFoundException {
        Optional<Reservation> myReservation = reservationRepository.findByCarOwnerIdAndStatus(carOwnerId, RESERVED);
        Optional<ParkingLot> parkingLot = parkingLotService.getParkingLot(myReservation.get().getParkingLotId());

        MyReservationMapper reservationMapper = new MyReservationMapper(myReservation,parkingLot);

        return reservationMapper.mappedResponse();
    }

    public CarOwner getCarOwnerById(Long id) {
        return carOwnerRepository.findCarOwnerById(id);
    }
}
