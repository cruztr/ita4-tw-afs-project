package ita.tw.afs.spark.controller;

import ita.tw.afs.spark.model.ParkingBlock;
import ita.tw.afs.spark.service.ParkingBlockService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value ="/parkingBlock")
public class ParkingBlockController {

    private static final String PARKING_BLOCK_NOT_FOUND = "Parking Block Not Found";

    @Autowired
    private ParkingBlockService parkingBlockService;

    @GetMapping(produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public List<ParkingBlock> getParkingBlocks() {
        return parkingBlockService.getAll();
    }

    @GetMapping(value = "/{id}", produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public ParkingBlock getParkingBlock(@PathVariable Long id) throws NotFoundException {
        Optional<ParkingBlock> parkingBlockOptional = parkingBlockService.getParkingBlock(id);

        if(parkingBlockOptional.isPresent())
            return parkingBlockOptional.get();

        throw  new NotFoundException(PARKING_BLOCK_NOT_FOUND);
    }

}
