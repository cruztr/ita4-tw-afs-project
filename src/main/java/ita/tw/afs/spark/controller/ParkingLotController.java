package ita.tw.afs.spark.controller;

import ita.tw.afs.spark.exception.InvalidCredentialsException;
import ita.tw.afs.spark.model.ParkingBlock;
import ita.tw.afs.spark.service.ParkingBlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value ="/parkingLot")
public class ParkingLotController {

    @Autowired
    private ParkingBlockService parkingBlockService;

    @GetMapping(value = "/{id}/parkingBlock", produces = {"application/json"})
    public List<ParkingBlock> getParkingBlocks(@PathVariable Long id) throws InvalidCredentialsException {
        return parkingBlockService.getParkingLotSpaces(id);
    }
}
