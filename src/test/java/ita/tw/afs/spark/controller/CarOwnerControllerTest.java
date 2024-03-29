package ita.tw.afs.spark.controller;

import ita.tw.afs.spark.exception.ExistingCredentialException;
import ita.tw.afs.spark.exception.InvalidCredentialsException;
import ita.tw.afs.spark.model.CarOwner;
import ita.tw.afs.spark.model.Reservation;
import ita.tw.afs.spark.service.CarOwnerService;
import ita.tw.afs.spark.service.LogsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static ita.tw.afs.spark.testUtil.TestUtil.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CarOwnerController.class)
@ActiveProfiles(profiles = "test")
public class CarOwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarOwnerService carOwnerService;

    @MockBean
    private LogsService logsService;

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
    void should_return_status_created_when_create_reservation() throws Exception {
        Reservation reservation = new Reservation();
        final Long parkingLotId = (long) 1;

        when(carOwnerService.createReservation(reservation, parkingLotId)).thenReturn(reservation);

        ResultActions result = mockMvc.perform(post("/spark/carOwner/parkingLot/{parkingLotId}",parkingLotId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(reservation)));
        result.andExpect(status().isCreated());
    }

    @Test
    public void should_return_carOwner_account_when_correct_username_password() throws Exception {
        when(carOwnerService.login("mikeCrophones07", "Amikewashicho")).thenReturn(carOwner);
        ResultActions resultActions = mockMvc.perform(post("/spark/carOwner/login")
                .content(asJsonString(carOwner))
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(carOwner.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(carOwner.getLastName())));
    }

    @Test
    public void should_return_error_object_when_incorrect_username_password() throws Exception {
        doThrow(InvalidCredentialsException.class).when(carOwnerService).login("mikeCrophones07", "Amikewashicho");
        ResultActions resultActions = mockMvc.perform(post("/spark/carOwner/login")
                .content(asJsonString(carOwner))
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isNotFound());
    }

    @Test
    void should_return_ok_when_carOwner_sign_up_account() throws Exception {
        when(carOwnerService.signUp(carOwner)).thenReturn(carOwner);

        ResultActions resultActions = mockMvc.perform(post("/spark/carOwner/signUp")
                .content(asJsonString(carOwner))
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());
    }

    @Test
    void should_return_error_when_carOwner_sign_up_account_credential_is_existing() throws Exception {
        when(carOwnerService.signUp(anyObject())).thenThrow(new ExistingCredentialException("Invalid"));

        ResultActions resultActions = mockMvc.perform(post("/spark/carOwner/signUp")
                .content(asJsonString(carOwner))
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isNotFound());
    }
}