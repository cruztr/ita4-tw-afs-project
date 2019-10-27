package ita.tw.afs.spark.service;

import ita.tw.afs.spark.model.CarOwner;
import ita.tw.afs.spark.model.Reservation;
import ita.tw.afs.spark.repository.CarOwnerRepository;
import ita.tw.afs.spark.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class CarOwnerService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CarOwnerRepository carOwnerRepository;

    public Reservation createReservation(Reservation reservation) {
        Date date = new Date();
        CarOwner carOwner = new CarOwner();
        carOwner.setFirstName("Genrev");
        carOwner.setLastName("Arambulo");
        carOwner.setPlateNumber("PAX-1726");

        for(int ctr = 0; ctr<10; ctr++){
            Reservation reservation1 = new Reservation();
            reservation1.setApplicationTime(reservation.getApplicationTime());
            reservation1.setReservedTime(reservation.getReservedTime());
            reservation1.setStatus(reservation.getStatus());
            reservation1.setApplicationTime(date.toString());
            reservation1.setCarOwner(carOwner);
            reservationRepository.save(reservation1);

        }
        carOwnerRepository.save(carOwner);
        return reservation;
    }
}