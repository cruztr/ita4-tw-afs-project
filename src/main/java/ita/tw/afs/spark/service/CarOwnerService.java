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

    public static final String RESERVED = "RESERVED";
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CarOwnerRepository carOwnerRepository;

    public Reservation createReservation(Reservation reservation) {
        Date date = new Date();
        reservation.setStatus(RESERVED);
        reservation.setApplicationTime(date.toString());

        return reservationRepository.save(reservation);
    }
}
