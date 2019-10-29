package ita.tw.afs.spark.controller;

import ita.tw.afs.spark.dto.ReservationResponse;
import ita.tw.afs.spark.exception.InvalidCredentialsException;
import ita.tw.afs.spark.model.ParkingBoy;
import ita.tw.afs.spark.model.Reservation;
import ita.tw.afs.spark.service.ParkingBoyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value ="/spark/parkingBoy")
public class ParkingBoyController {

    @Autowired
    private final ParkingBoyService parkingBoyService;

    public ParkingBoyController(ParkingBoyService parkingBoyService){
        this.parkingBoyService = parkingBoyService;
    }

    @PostMapping(produces = {"application/json"})
    public ParkingBoy add(@RequestBody ParkingBoy parkingBoy) {
        return parkingBoyService.save(parkingBoy);
    }

    @PostMapping(value = "/login", produces = {"application/json"}, consumes = {"application/json"})
    public ParkingBoy login(@RequestBody ParkingBoy parkingBoy) throws InvalidCredentialsException {
        return parkingBoyService.login(parkingBoy.getUsername(), parkingBoy.getPassword());
    }

    @GetMapping(value = "/reservations", produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public List<ReservationResponse> getReservations() {
        return parkingBoyService.getReservations();
    }

    @GetMapping(value = "/getPendingReservations", produces = {"application/json"})
    public List<Reservation> getPendingReservations() {
        return parkingBoyService.getPendingReservations();
    }

    @GetMapping(value = "/{parkingBoyId}", produces = {"application/json"})
    public ParkingBoy get(@PathVariable Long parkingBoyId) {
        return parkingBoyService.getParkingBoyById(parkingBoyId);
    }
}
