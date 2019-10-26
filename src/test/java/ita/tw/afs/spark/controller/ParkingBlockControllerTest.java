package ita.tw.afs.spark.controller;

import ita.tw.afs.spark.model.ParkingBlock;
import ita.tw.afs.spark.model.ParkingLot;
import ita.tw.afs.spark.service.ParkingBlockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ParkingBlockController.class)
@ActiveProfiles(profiles = "ParkingBlockControllerTest")
class ParkingBlockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParkingBlockService parkingBlockService;

    @Test
    void should_return_status_ok_when_get_all_parking_blocks() throws Exception {
        when(parkingBlockService.getAll()).thenReturn(singletonList(new ParkingBlock()));

        ResultActions resultActions = mockMvc.perform(get("/parkingBlock"));
        resultActions.andExpect(status().isOk());
    }

    @Test
    void should_return_parkingLot_when_get_specific_parkingLot() throws Exception {
        when(parkingBlockService.getParkingBlock(1L)).thenReturn(Optional.of(new ParkingBlock(1L, 1, new ParkingLot())));

        ResultActions resultActions = mockMvc.perform(get("/parkingBlock/{id}", "1"));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.position", is(1)))
                .andExpect(jsonPath("$.status", is("AVAILABLE")));
    }

    @Test
    void should_raise_notFoundException_when_parkingLot_is_not_found() throws Exception {
        when(parkingBlockService.getParkingBlock(1L)).thenReturn(Optional.of(new ParkingBlock()));

        ResultActions resultActions = mockMvc.perform(get("/parkingLot/{id}", "2"));

        resultActions.andExpect(status().isNotFound());
    }
}