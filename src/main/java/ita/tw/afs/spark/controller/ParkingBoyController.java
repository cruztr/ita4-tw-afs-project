package ita.tw.afs.spark.controller;

import ita.tw.afs.spark.exception.InvalidCredentialsException;
import ita.tw.afs.spark.model.ParkingBoy;
import ita.tw.afs.spark.service.ParkingBoyService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value ="/spark")
public class ParkingBoyController {

    private final ParkingBoyService parkingBoyService;

    public ParkingBoyController(ParkingBoyService parkingBoyService){
        this.parkingBoyService = parkingBoyService;
    }

    @PostMapping(value = "/parkingBoy", produces = {"application/json"})
    public ParkingBoy add(@RequestBody ParkingBoy parkingBoy) {
        return parkingBoyService.save(parkingBoy);
    }

    @PostMapping(value = "/parkingBoy/login", produces = {"application/json"}, consumes = {"application/json"})
    public ParkingBoy login(@RequestBody ParkingBoy parkingBoy) throws InvalidCredentialsException {
        return parkingBoyService.login(parkingBoy.getUsername(), parkingBoy.getPassword());
    }
}
