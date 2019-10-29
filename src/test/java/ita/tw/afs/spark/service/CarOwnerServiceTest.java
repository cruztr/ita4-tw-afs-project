package ita.tw.afs.spark.service;

import ita.tw.afs.spark.exception.ExistingCredentialException;
import ita.tw.afs.spark.exception.InvalidCredentialsException;
import ita.tw.afs.spark.model.CarOwner;
import ita.tw.afs.spark.model.ParkingBlock;
import ita.tw.afs.spark.model.ParkingLot;
import ita.tw.afs.spark.model.Reservation;
import ita.tw.afs.spark.repository.CarOwnerRepository;
import ita.tw.afs.spark.repository.ParkingLotRepository;
import ita.tw.afs.spark.repository.ReservationRepository;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CarOwnerService.class)
@ActiveProfiles(profiles = "CarOwnerServiceTest")
class CarOwnerServiceTest {

    public static final String RESERVED = "RESERVED";
    public static final String AVAILABLE = "AVAILABLE";

    @MockBean
    private ReservationRepository reservationRepository;

    @MockBean
    private CarOwnerRepository carOwnerRepository;

    @MockBean
    ParkingLotService parkingLotService;

    @MockBean
    private ParkingBlockService parkingBlockService;

    @Autowired
    private CarOwnerService carOwnerService;

    private CarOwner carOwner;

    private Reservation reservation;

    private ParkingBlock parkingBlock;

    private Long parkingLotId;

    @BeforeEach
    public void setUp(){
        parkingLotId =  1L;
        carOwner = new CarOwner();
        carOwner.setFirstName("Mike");
        carOwner.setLastName("Echon");
        carOwner.setPassword("Amikewashicho");
        carOwner.setUsername("mikeCrophones07");
        carOwner.setPlateNumber("PLT=2734");
        reservation = new Reservation();
        reservation.setStatus(RESERVED);
        reservation.setReservationNumber(1L);
        reservation.setParkingLotId(parkingLotId);
        reservation.setPosition(1);
        parkingBlock = new ParkingBlock();
        parkingBlock.setParkingLotId(parkingLotId);
        parkingBlock.setId(1L);
        parkingBlock.setPosition(1);
        parkingBlock.setStatus(AVAILABLE);
    }

    @Test
    void should_return_reservation_when_information_is_valid() throws NotFoundException {
        List<ParkingBlock> parkingBlockList = new ArrayList<>();
        parkingBlockList.add(parkingBlock);

        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setParkingBlocks(parkingBlockList);


        when(parkingLotService.getParkingLot(parkingLotId)).thenReturn(Optional.of(parkingLot));
        when(reservationRepository.save(anyObject())).thenReturn(reservation);

        Reservation myReservation = carOwnerService.createReservation(reservation, parkingLotId);
        assertThat(reservation, is(myReservation));
    }

    @Test
    void should_cancel_reservation_when_information_is_valid() throws NotFoundException {
        parkingBlock.setStatus(RESERVED);
        reservation.setStatus(RESERVED);
        Optional<Reservation> reservationOptional = Optional.of(reservation);
        when(reservationRepository.findById(anyLong())).thenReturn(reservationOptional);
        when(parkingBlockService.findByParkingLotIdAndPosition(anyLong(), anyInt()))
                .thenReturn(parkingBlock);
        parkingBlockService.save(parkingBlock);
        doNothing().when(parkingBlockService).save(parkingBlock);
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        assertThat(carOwnerService.cancelReservation(reservation.getReservationNumber()), is(reservationOptional));
    }
    @Test
    void should_return_carOwner_when_correct_credentials_is_passed() throws InvalidCredentialsException {
        when(carOwnerRepository.findByUsernameAndPassword("mikeCrophones07", "Amikewashicho"))
                .thenReturn(carOwner);

        CarOwner actualCarOwner = carOwnerService.login(carOwner.getUsername(), carOwner.getPassword());
        assertThat(actualCarOwner, is(carOwner));
    }

    @Test
    void should_return_error_when_incorrect_credentials_is_passed() {
         when(carOwnerRepository.findByUsername("mikeCrophones07"))
                 .thenReturn(null);

        assertThrows(InvalidCredentialsException.class,
                () -> carOwnerService.login(carOwner.getUsername(), carOwner.getPassword()));
    }

    @Test
    void should_return_ok_when_credential_for_sign_up_is_correct() throws ExistingCredentialException, NotFoundException {
        when(carOwnerRepository.findByUsername("mikeCrophones07"))
                .thenReturn(null);
        when(carOwnerRepository.save(carOwner)).thenReturn(carOwner);

        CarOwner actualCarOwner = carOwnerService.signUp(carOwner);
        assertThat(actualCarOwner, is(carOwner));
    }

    @Test
    void should_return_error_when_credential_for_sign_up_is_existing(){
        when(carOwnerRepository.findByUsername("mikeCrophones07"))
                .thenReturn(carOwner);

        assertThrows(ExistingCredentialException.class,
                () -> carOwnerService.signUp(carOwner));
    }

    @Test
    void should_return_error_when_some_fields_are_empty(){
        carOwner.setFirstName("");
        when(carOwnerRepository.findByUsername("mikeCrophones07"))
                .thenReturn(carOwner);

        assertThrows(NotFoundException.class,
                () -> carOwnerService.signUp(carOwner));
    }

    @Test
    void should_return_error_when_password_is_less_than_8(){
        carOwner.setPassword("12345");
        when(carOwnerRepository.findByUsername("mikeCrophones07"))
                .thenReturn(carOwner);

        assertThrows(NotFoundException.class,
                () -> carOwnerService.signUp(carOwner));
    }


}