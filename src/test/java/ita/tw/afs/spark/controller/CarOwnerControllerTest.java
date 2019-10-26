package ita.tw.afs.spark.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ita.tw.afs.spark.model.Reservation;
import ita.tw.afs.spark.service.CarOwnerService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CarOwnerController.class)
@ActiveProfiles(profiles = "test")
public class CarOwnerControllerTest {

    @Autowired
    private MockMvc mockmvc;

    @MockBean
    private CarOwnerService carOwnerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void should_return_status_created_when_create_reservation() throws Exception {
        Reservation reservation = new Reservation();

        when(carOwnerService.createReservation(reservation)).thenReturn(reservation);

        ResultActions result = mockmvc.perform(post("/sparks/carOwner")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(reservation)));
        result.andExpect(status().isCreated());
    }
}