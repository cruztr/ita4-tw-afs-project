package ita.tw.afs.spark.controller;

import ita.tw.afs.spark.exception.ExistingCredentialException;
import ita.tw.afs.spark.exception.InvalidCredentialsException;
import ita.tw.afs.spark.model.CarOwner;
import ita.tw.afs.spark.model.Reservation;
import ita.tw.afs.spark.service.CarOwnerService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value ="/spark/carOwner")
public class CarOwnerController {

    @Autowired
    private CarOwnerService carOwnerService;

    @PostMapping(value = "parkingLot/{parkingLotId}", produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.CREATED)
    public Reservation createReservation(@RequestBody Reservation reservation,
                                         @PathVariable Long parkingLotId) throws NotFoundException {
        return carOwnerService.createReservation(reservation, parkingLotId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/reservation/{reservationId}", produces = APPLICATION_JSON_VALUE)
    public Optional<Reservation> cancelReservation(@PathVariable Long reservationId) throws NotFoundException {
        return carOwnerService.cancelReservation(reservationId);
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
