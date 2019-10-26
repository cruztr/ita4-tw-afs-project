package ita.tw.afs.spark.service;

import ita.tw.afs.spark.dto.ReservationResponse;
import ita.tw.afs.spark.exception.InvalidCredentialsException;
import ita.tw.afs.spark.mapper.ReservationMapper;
import ita.tw.afs.spark.model.ParkingBoy;
import ita.tw.afs.spark.model.Reservation;
import ita.tw.afs.spark.repository.ParkingBoyRepository;
import ita.tw.afs.spark.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingBoyService {

    public static final String STATUS = "RESERVED";

    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public ParkingBoy save(ParkingBoy parkingBoy) {
        return parkingBoyRepository.save(parkingBoy);
    }

    public ParkingBoy login(String username, String password) throws InvalidCredentialsException {
        ParkingBoy parkingBoyAccount = parkingBoyRepository.findByUsernameAndPassword(username, password);
        if (parkingBoyAccount != null) {
            return parkingBoyAccount;
        }
        throw new InvalidCredentialsException("Incorrect username/password.");
    }

    public List<ReservationResponse> getReservations() {
        List<ReservationResponse> reservationResponses = new ArrayList<>();
        reservationRepository.findByStatus(STATUS).forEach(reservation -> {
            ReservationMapper reservationMapper = new ReservationMapper(reservation);
            reservationResponses.add(reservationMapper.mappedResponse());
        });
        return reservationResponses;
    }
}
