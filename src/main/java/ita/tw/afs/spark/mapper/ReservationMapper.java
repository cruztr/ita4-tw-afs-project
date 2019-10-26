package ita.tw.afs.spark.mapper;

import ita.tw.afs.spark.dto.ReservationResponse;
import ita.tw.afs.spark.model.CarOwner;
import ita.tw.afs.spark.model.Reservation;

public class ReservationMapper {
    private final Reservation reservation;

    public ReservationMapper(Reservation reservation){
        this.reservation = reservation;
    }

    public ReservationResponse mappedResponse(){
        ReservationResponse reservationResponse = new ReservationResponse();
        String firstName = reservation.getCarOwner().getFirstName();
        String lastName = reservation.getCarOwner().getLastName();

        reservationResponse.setFullName(firstName + " " + lastName);
        reservationResponse.setPlateNumber(reservation.getCarOwner().getPlateNumber());
        reservationResponse.setReservedTime(reservation.getReservedTime());
        reservationResponse.setApplicationTime(reservation.getApplicationTime());
        reservationResponse.setStatus(reservation.getStatus());
        reservationResponse.setReservationNumber(reservation.getReservationNumber());
        return reservationResponse;
    }
}
