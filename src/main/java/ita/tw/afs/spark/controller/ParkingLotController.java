package ita.tw.afs.spark.controller;

import ita.tw.afs.spark.dto.ParkingLotResponse;
import ita.tw.afs.spark.model.ParkingLot;
import ita.tw.afs.spark.model.Reservation;
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
    public ParkingLot createParkingLot(@RequestBody ParkingLot parkingLot) throws NotFoundException {
        return parkingLotService.saveLotAndCreateBlocks(parkingLot);
    }

    @GetMapping(produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public List<ParkingLot> getAllParkingLots(){
        return parkingLotService.getAll();
    }

    @GetMapping(path = "/available", produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public List<ParkingLotResponse> getAvailableParkingLots(){
        return parkingLotService.getAvailableParkingLots();
    }

    @GetMapping(value = "/{id}", produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public ParkingLot getParkingLot(@PathVariable Long id) throws NotFoundException {
        Optional<ParkingLot> parkingLotOptional = parkingLotService.getParkingLot(id);

        if(parkingLotOptional.isPresent())
            return parkingLotOptional.get();

        throw  new NotFoundException(PARKING_LOT_NOT_FOUND);
    }

    @GetMapping(value = "/all", produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public List<ParkingLotResponse> getParkingLotsAvailability(){
        return parkingLotService.getParkingLotsWithStatus();
    }

    @GetMapping(path = "/{id}/parkingBlock/{position}", produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public Reservation getReservation(@PathVariable Long id, @PathVariable Integer position){
        return parkingLotService.getReservation(id, position);
    }
}
