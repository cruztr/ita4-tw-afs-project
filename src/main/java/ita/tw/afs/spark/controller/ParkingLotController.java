package ita.tw.afs.spark.controller;

import ita.tw.afs.spark.exception.InvalidCredentialsException;
import ita.tw.afs.spark.model.ParkingBlock;
import ita.tw.afs.spark.model.ParkingLot;
import ita.tw.afs.spark.service.OrdersService;
import ita.tw.afs.spark.service.ParkingBlockService;
import ita.tw.afs.spark.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value ="/parkingLot")
public class ParkingLotController {

    @Autowired
    private ParkingBlockService parkingBlockService;

    @Autowired
    private ParkingLotService parkingLotService;

    @GetMapping(value = "/{id}/parkingBlock", produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public List<ParkingBlock> getParkingBlocks(@PathVariable Long id) {
        return parkingBlockService.getParkingLotSpaces(id);
    }

    @PostMapping(produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.CREATED)
    public ParkingLot createParkingLot(@RequestBody ParkingLot parkingLot) {
        return parkingLotService.save(parkingLot);
    }
}
