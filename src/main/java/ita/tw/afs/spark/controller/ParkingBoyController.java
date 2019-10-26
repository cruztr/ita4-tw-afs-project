package ita.tw.afs.spark.controller;

import ita.tw.afs.spark.exception.InvalidCredentialsException;
import ita.tw.afs.spark.model.ParkingBlock;
import ita.tw.afs.spark.model.ParkingBoy;
import ita.tw.afs.spark.model.Reservation;
import ita.tw.afs.spark.service.ParkingBoyService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value ="/sparks/parkingBoy")
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

    @GetMapping(value = "/login", produces = {"application/json"}, consumes = {"application/json"})
    public ParkingBoy login(@RequestBody ParkingBoy parkingBoy) throws InvalidCredentialsException {
        return parkingBoyService.login(parkingBoy.getUsername(), parkingBoy.getPassword());
    }

    @GetMapping(value = "/reservations", produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public List<Reservation> getReservations() {
        return parkingBoyService.getReservations();
    }
}
