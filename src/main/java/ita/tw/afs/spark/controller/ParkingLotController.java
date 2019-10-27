package ita.tw.afs.spark.controller;

import ita.tw.afs.spark.model.ParkingLot;
import ita.tw.afs.spark.service.ParkingLotService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value ="/parkingLot")
public class ParkingLotController {

    private static final String PARKING_LOT_NOT_FOUND = "Parking Lot Not Found";

    @Autowired
    private ParkingLotService parkingLotService;

    @PostMapping(produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.CREATED)
    public ParkingLot createParkingLot(@RequestBody ParkingLot parkingLot) {
        return parkingLotService.saveLotAndCreateBlocks(parkingLot);
    }

    @GetMapping(produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public List<ParkingLot> getAllParkingLots(){
        return parkingLotService.getAll();
    }

    @GetMapping(value = "/{id}", produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public ParkingLot getParkingLot(@PathVariable Long id) throws NotFoundException {
        Optional<ParkingLot> parkingLotOptional = parkingLotService.getParkingLot(id);

        if(parkingLotOptional.isPresent())
            return parkingLotOptional.get();

        throw  new NotFoundException(PARKING_LOT_NOT_FOUND);
    }
}
