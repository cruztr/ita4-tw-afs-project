package ita.tw.afs.spark.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ita.tw.afs.spark.model.ParkingBlock;
import ita.tw.afs.spark.model.ParkingLot;
import ita.tw.afs.spark.service.ParkingBlockService;
import ita.tw.afs.spark.service.ParkingLotService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(ParkingLotController.class)
@ActiveProfiles(profiles = "ParkingLotControllerTest")
class ParkingLotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParkingBlockService parkingBoyService;

    @MockBean
    private ParkingLotService parkingLotService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void should_return_status_ok_when_get_all_parking_blocks() throws Exception {
        List<ParkingBlock> parkingBlock = new ArrayList<>();
        when(parkingBoyService.getParkingLotSpaces(1L)).thenReturn(parkingBlock);

        ResultActions resultActions = mockMvc.perform(get("/parkingLot/{id}/parkingBlock", "1"));
        resultActions.andExpect(status().isOk());
    }

    @Test
    void should_return_status_created_when_create_parking_lot() throws Exception {
        ParkingLot parkingLot = new ParkingLot();
        when(parkingLotService.saveLotAndCreateBlocks(parkingLot)).thenReturn(parkingLot);

        ResultActions resultActions = mockMvc.perform(post("/parkingLot")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(parkingLot)));

        resultActions.andExpect(status().isCreated());
    }


    @Test
    void should_return_list_of_parkingLots_when_get_all_parkingLots() throws Exception {
        when(parkingLotService.getAll()).thenReturn(singletonList(new ParkingLot()));

        ResultActions resultActions = mockMvc.perform(get("/parkingLot"));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void should_return_parkingLot_when_get_specific_parkingLot() throws Exception {
        when(parkingLotService.getParkingLot(1L)).thenReturn(Optional.of(new ParkingLot(1L, "MAAX", 20)));

        ResultActions resultActions = mockMvc.perform(get("/parkingLot/{id}", "1"));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("MAAX")))
                .andExpect(jsonPath("$.capacity", is(20)));
    }

    @Test
    void should_raise_notFoundException_when_parkingLot_is_not_found() throws Exception {
        when(parkingLotService.getParkingLot(1L)).thenReturn(Optional.of(new ParkingLot()));

        ResultActions resultActions = mockMvc.perform(get("/parkingLot/{id}", "2"));

        resultActions.andExpect(status().isNotFound());
    }
}