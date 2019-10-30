package ita.tw.afs.spark.mapper;

import ita.tw.afs.spark.dto.ReservationResponse;
import ita.tw.afs.spark.model.CarOwner;
import ita.tw.afs.spark.model.ParkingLot;
import ita.tw.afs.spark.model.Reservation;

public class ReservationMapper {
    private final Reservation reservation;
    private final CarOwner carOwner;
    private final ParkingLot parkingLot;

    public ReservationMapper(Reservation reservation, CarOwner carOwner, ParkingLot parkingLot){
        this.reservation = reservation;
        this.carOwner = carOwner;
        this.parkingLot = parkingLot;
    }

    public ReservationResponse mappedResponse(){
        ReservationResponse reservationResponse = new ReservationResponse();
        String firstName = carOwner.getFirstName();
        String lastName = carOwner.getLastName();

        reservationResponse.setFullName(firstName + " " + lastName);
        reservationResponse.setParkingLotId(reservation.getParkingLotId());
        reservationResponse.setCarOwnerId(reservation.getCarOwnerId());
        reservationResponse.setPlateNumber(carOwner.getPlateNumber());
        reservationResponse.setReservedTime(reservation.getReservedTime());
        reservationResponse.setApplicationTime(reservation.getApplicationTime());
        reservationResponse.setStatus(reservation.getStatus());
        reservationResponse.setReservationNumber(reservation.getReservationNumber());
        reservationResponse.setPosition(reservation.getPosition());
        reservationResponse.setKey(reservation.getReservationNumber());
        reservationResponse.setParkingLotName(parkingLot.getName());
        reservationResponse.setParkingLotLocation(parkingLot.getLocation());
        reservationResponse.setRate(parkingLot.getRate());
        return reservationResponse;
    }
}
