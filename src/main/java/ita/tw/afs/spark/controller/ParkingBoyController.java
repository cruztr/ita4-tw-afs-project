package ita.tw.afs.spark.controller;

import ita.tw.afs.spark.exception.InvalidCredentialsException;
import ita.tw.afs.spark.model.ParkingBlock;
import ita.tw.afs.spark.model.ParkingBoy;
import ita.tw.afs.spark.service.ParkingBoyService;
import javassist.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value ="/parkingBoy")
public class ParkingBoyController {

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
}
