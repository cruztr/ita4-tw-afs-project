package ita.tw.afs.spark.controller;

import ita.tw.afs.spark.exception.ExistingCredentialException;
import ita.tw.afs.spark.exception.InvalidCredentialsException;
import ita.tw.afs.spark.model.CarOwner;
import ita.tw.afs.spark.model.Reservation;
import ita.tw.afs.spark.service.CarOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value ="/spark/carOwner")
public class CarOwnerController {

    @Autowired
    private CarOwnerService carOwnerService;

    @PostMapping(produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.CREATED)
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return carOwnerService.createReservation(reservation);
    }

    @PostMapping(value = "/login", produces = {"application/json"})
    public CarOwner login(@RequestBody CarOwner carOwner) throws InvalidCredentialsException {
        return carOwnerService.login(carOwner.getUsername(), carOwner.getPassword());
    }

    @PostMapping(value = "/signUp", produces = {"application/json"})
    public CarOwner signUp(@RequestBody CarOwner carOwner) throws ExistingCredentialException {
        CarOwner finalCarOwner = carOwnerService.signUp(carOwner);
        return finalCarOwner;
    }
}
