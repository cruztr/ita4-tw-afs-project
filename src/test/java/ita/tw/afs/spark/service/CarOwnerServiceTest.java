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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyObject;
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

    @BeforeEach
    public void setUp(){
        carOwner = new CarOwner();
        carOwner.setFirstName("Mike");
        carOwner.setLastName("Echon");
        carOwner.setPassword("Amikewashicho");
        carOwner.setUsername("mikeCrophones07");
        carOwner.setPlateNumber("PLT=2734");
    }

    @Test
    void should_return_reservation_when_information_is_valid() throws NotFoundException {
        final Long parkingLotId = (long) 1;
        final Integer position = 19;

        ParkingBlock parkingBlock = new ParkingBlock();
        parkingBlock.setParkingLotId(parkingLotId);
        parkingBlock.setId(1L);
        parkingBlock.setPosition(1);
        parkingBlock.setStatus(AVAILABLE);

        List<ParkingBlock> parkingBlockList = new ArrayList<>();
        parkingBlockList.add(parkingBlock);

        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setParkingBlocks(parkingBlockList);

        reservation = new Reservation();
        reservation.setStatus(RESERVED);
        reservation.setParkingLotId(parkingLotId);

        when(parkingLotService.getParkingLot(parkingLotId)).thenReturn(Optional.of(parkingLot));
        when(reservationRepository.save(anyObject())).thenReturn(reservation);

        Reservation myReservation = carOwnerService.createReservation(reservation, parkingLotId);
        Assert.assertThat(reservation, is(myReservation));
    }

    @Test
    void should_return_carOwner_when_correct_credentials_is_passed() throws InvalidCredentialsException {
        when(carOwnerRepository.findByUsernameAndPassword("mikeCrophones07", "Amikewashicho"))
                .thenReturn(carOwner);

        CarOwner actualCarOwner = carOwnerService.login(carOwner.getUsername(), carOwner.getPassword());
        Assert.assertThat(actualCarOwner, is(carOwner));
    }

    @Test
    void should_return_error_when_incorrect_credentials_is_passed() {
         when(carOwnerRepository.findByUsernameAndPassword("mikeCrophones07", "Amikewashicho"))
                 .thenReturn(null);

        assertThrows(InvalidCredentialsException.class,
                () -> carOwnerService.login(carOwner.getUsername(), carOwner.getPassword()));
    }

    @Test
    void should_return_ok_when_credential_for_sign_up_is_correct() throws ExistingCredentialException {
        when(carOwnerRepository.findByUsernameOrPassword("mikeCrophones07", "Amikewashicho"))
                .thenReturn(null);
        when(carOwnerRepository.save(carOwner)).thenReturn(carOwner);

        CarOwner actualCarOwner = carOwnerService.signUp(carOwner);
        Assert.assertThat(actualCarOwner, is(carOwner));
    }

    @Test
    void should_return_error_when_credential_for_sign_up_is_existing() throws ExistingCredentialException {
        when(carOwnerRepository.findByUsernameOrPassword("mikeCrophones07", "Amikewashicho"))
                .thenReturn(carOwner);

        assertThrows(ExistingCredentialException.class,
                () -> carOwnerService.signUp(carOwner));
    }
}