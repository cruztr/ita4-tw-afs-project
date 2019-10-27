package ita.tw.afs.spark.controller;

import ita.tw.afs.spark.model.Reservation;
import ita.tw.afs.spark.service.CarOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value ="/spark/carOwner")
public class CarOwnerController {

    @Autowired
    private CarOwnerService carOwnerService;

    @PostMapping(produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.CREATED)
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return carOwnerService.createReservation(reservation);
    }
}
