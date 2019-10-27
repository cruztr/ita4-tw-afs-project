package ita.tw.afs.spark.service;

import ita.tw.afs.spark.exception.InvalidCredentialsException;
import ita.tw.afs.spark.model.CarOwner;
import ita.tw.afs.spark.repository.CarOwnerRepository;
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

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CarOwnerService.class)
@ActiveProfiles(profiles = "CarOwnerServiceTest")
class CarOwnerServiceTest {

    @MockBean
    private CarOwnerRepository carOwnerRepository;

    @MockBean
    private ReservationRepository reservationRepository;

    @Autowired
    private CarOwnerService carOwnerService;

    private CarOwner carOwner;

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

        assertThrows(InvalidCredentialsException.class, () -> carOwnerService.login(carOwner.getUsername(), carOwner.getPassword()));
    }
}