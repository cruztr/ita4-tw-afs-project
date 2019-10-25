package ita.tw.afs.spark.controller;

import ita.tw.afs.spark.model.ParkingBlock;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(ParkingLotController.class)
@ActiveProfiles(profiles = "ParkingLotControllerTest")
class ParkingLotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParkingBlockService parkingBoyService;

    @Test
    void should_return_status_ok_when_get_all_parking_blocks() throws Exception {
        List<ParkingBlock> parkingBlock = new ArrayList<>();
        when(parkingBoyService.getParkingLotSpaces(1L)).thenReturn(parkingBlock);

        ResultActions resultActions = mockMvc.perform(get("/parkingLot/{id}/parkingBlock", "1"));
        resultActions.andExpect(status().isOk());
    }
}