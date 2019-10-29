package ita.tw.afs.spark.service;

import ita.tw.afs.spark.dto.ReservationResponse;
import ita.tw.afs.spark.exception.InvalidCredentialsException;
import ita.tw.afs.spark.mapper.ReservationMapper;
import ita.tw.afs.spark.model.CarOwner;
import ita.tw.afs.spark.model.ParkingBoy;
import ita.tw.afs.spark.model.Reservation;
import ita.tw.afs.spark.repository.CarOwnerRepository;
import ita.tw.afs.spark.repository.ParkingBoyRepository;
import ita.tw.afs.spark.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingBoyService {
    public static final String STATUS = "RESERVED";

    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CarOwnerRepository carOwnerRepository;

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
        List<Reservation> reservationList = reservationRepository.findByStatus(STATUS);
        for (Reservation reservation: reservationList) {
            CarOwner fetchedCarOwner = getOwner(reservation.getCarOwnerId());
            ReservationMapper reservationMapper = new ReservationMapper(reservation, fetchedCarOwner);
            reservationResponses.add(reservationMapper.mappedResponse());
        }
        return reservationResponses;
    }

    public CarOwner getOwner(Long id){
        Optional<CarOwner> fetchedCarOwner = carOwnerRepository.findById(id);
        return fetchedCarOwner.get();
    }

    public List<Reservation> getPendingReservations() {
        return reservationRepository.getReservationsByStatus("RESERVED");
    }
}
