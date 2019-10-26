package ita.tw.afs.spark.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ita.tw.afs.spark.exception.InvalidCredentialsException;
import ita.tw.afs.spark.model.ParkingBoy;
import ita.tw.afs.spark.service.ParkingBoyService;
import javassist.NotFoundException;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest({ParkingBoyController.class})
@ActiveProfiles(profiles = "test")
class ParkingBoyControllerTest {
    @Autowired
    ParkingBoyController parkingBoyController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParkingBoyService parkingBoyService;

    @Autowired
    private ObjectMapper objectMapper;

    private ParkingBoy parkingBoy;
    @BeforeEach
    public void setUp(){
        parkingBoy = new ParkingBoy();
        parkingBoy.setUsername("username");
        parkingBoy.setPassword("password");
        parkingBoy.setFirstName("Ken");
        parkingBoy.setLastName("Sevilla");
    }

    @Test
    public void should_return_parking_boy_account_when_correct_username_password() throws Exception {
        when(parkingBoyService.login("username", "password")).thenReturn(parkingBoy);
        ResultActions resultActions = mockMvc.perform(post("/parkingBoy/login")
                .content(asJsonString(parkingBoy))
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(parkingBoy.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(parkingBoy.getLastName())));
    }

    @Test
    public void should_return_error_object_when_incorrect_username_password() throws Exception {
        doThrow(InvalidCredentialsException.class).when(parkingBoyService).login("username", "password");
        ResultActions resultActions = mockMvc.perform(post("/parkingBoy/login")
                .content(asJsonString(parkingBoy))
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isNotFound());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}