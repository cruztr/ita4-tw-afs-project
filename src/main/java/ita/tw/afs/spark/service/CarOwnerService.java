package ita.tw.afs.spark.service;

import ita.tw.afs.spark.exception.ExistingCredentialException;
import ita.tw.afs.spark.exception.InvalidCredentialsException;
import ita.tw.afs.spark.model.CarOwner;
import ita.tw.afs.spark.model.ParkingBlock;
import ita.tw.afs.spark.model.Reservation;
import ita.tw.afs.spark.repository.CarOwnerRepository;
import ita.tw.afs.spark.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class CarOwnerService {

    public static final String RESERVED = "RESERVED";
    public static final String AVAILABLE = "AVAILABLE";
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CarOwnerRepository carOwnerRepository;

    public Reservation createReservation(Reservation reservation) {
        Date date = new Date();
        reservation.setStatus(RESERVED);
        reservation.setApplicationTime(date.toString());

        List<ParkingBlock> parkingBlockList = reservation.getParkingLot().getParkingBlocks();
        Integer parkingBlockPosition = getLastAvailableParkingBlock(parkingBlockList);
        reservation.setPosition(parkingBlockPosition);
        return reservationRepository.save(reservation);

    }

    private Integer getLastAvailableParkingBlock(List<ParkingBlock> parkingBlockList) {
        return parkingBlockList.stream()
                .filter(parkingBlock -> (parkingBlock.getStatus().equals(AVAILABLE)))
                .map(parkingBlock -> parkingBlock.getPosition())
                .reduce((x,y) -> y).orElse(0);
    }

    public CarOwner login(String username, String password) throws InvalidCredentialsException {
        CarOwner carOwner= carOwnerRepository.findByUsernameAndPassword(username, password);
        if(Objects.nonNull(carOwner)){
            return carOwner;
        }
        throw new InvalidCredentialsException("Incorrect username/password.");
    }

    public CarOwner signUp(CarOwner carOwner) throws ExistingCredentialException {
        CarOwner fetchedCarOwner = carOwnerRepository.findByUsernameOrPassword(carOwner.getUsername(), carOwner.getPassword());
        if(Objects.isNull(fetchedCarOwner)){
            return carOwnerRepository.save(carOwner);
        }
        throw new ExistingCredentialException("Username or Password is existing");
    }
}
