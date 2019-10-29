package ita.tw.afs.spark.mapper;

import ita.tw.afs.spark.dto.ReservationResponse;
import ita.tw.afs.spark.model.CarOwner;
import ita.tw.afs.spark.model.Reservation;
import ita.tw.afs.spark.repository.CarOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ReservationMapper {
    private final Reservation reservation;

    @Autowired
    private CarOwnerRepository carOwnerRepository;

    public ReservationMapper(Reservation reservation){
        this.reservation = reservation;
    }

    public ReservationResponse mappedResponse(){
        CarOwner carOwner = carOwnerRepository.findById(reservation.getCarOwnerId()).get();

        ReservationResponse reservationResponse = new ReservationResponse();
        String firstName = carOwner.getFirstName();
        String lastName = carOwner.getLastName();

        reservationResponse.setFullName(firstName + " " + lastName);
        reservationResponse.setPlateNumber(carOwner.getPlateNumber());
        reservationResponse.setReservedTime(reservation.getReservedTime());
        reservationResponse.setApplicationTime(reservation.getApplicationTime());
        reservationResponse.setStatus(reservation.getStatus());
        reservationResponse.setReservationNumber(reservation.getReservationNumber());
        reservationResponse.setKey(reservation.getReservationNumber());
        return reservationResponse;
    }
}
