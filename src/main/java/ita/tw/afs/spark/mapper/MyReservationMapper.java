package ita.tw.afs.spark.mapper;

import ita.tw.afs.spark.dto.MyReservationResponse;
import ita.tw.afs.spark.model.ParkingLot;
import ita.tw.afs.spark.model.Reservation;

import java.util.Optional;

public class MyReservationMapper {
    private final Optional<Reservation> reservation;
    private final Optional<ParkingLot> parkingLot;

    public MyReservationMapper(Optional<Reservation> reservation, Optional<ParkingLot> parkingLot) {
        this.reservation = reservation;
        this.parkingLot = parkingLot;
    }


    public MyReservationResponse mappedResponse() {
        MyReservationResponse reservationResponse = new MyReservationResponse();

        reservationResponse.setParkingLotId(parkingLot.get().getId());
        reservationResponse.setCapacity(parkingLot.get().getCapacity());
        reservationResponse.setParkingLotLocation(parkingLot.get().getLocation());
        reservationResponse.setParkingLotName(parkingLot.get().getName());
        reservationResponse.setReservationNumber(reservation.get().getReservationNumber());
        reservationResponse.setReservationStatus(reservation.get().getStatus());
        reservationResponse.setReservedTime(reservation.get().getReservedTime());
        reservationResponse.setParkingBlockPosition(reservation.get().getPosition());

        return reservationResponse;
    }
}