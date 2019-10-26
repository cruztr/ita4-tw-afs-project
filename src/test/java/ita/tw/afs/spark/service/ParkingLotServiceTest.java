package ita.tw.afs.spark.service;

import ita.tw.afs.spark.model.ParkingBlock;
import ita.tw.afs.spark.model.ParkingLot;
import ita.tw.afs.spark.repository.ParkingBlockRepository;
import ita.tw.afs.spark.repository.ParkingLotRepository;
import javassist.NotFoundException;
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

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@WebMvcTest(ParkingLotService.class)
@ActiveProfiles(profiles = "ParkingLotServiceTest")
class ParkingLotServiceTest {

    @MockBean
    ParkingLotRepository parkingLotRepository;

    @MockBean
    ParkingBlockRepository parkingBlockRepository;

    @Autowired
    ParkingLotService parkingLotService;

    private ParkingLot parkingLot;

    @BeforeEach
    void setUp() {
        parkingLot = new ParkingLot(1L, "ParkingLot1", 3);
        parkingLot.setParkingBlocks(buildParkingBlocks("AVAILABLE"));
    }

    @Test
    void should_get_available_parking_blocks() throws NotFoundException {
        when(parkingLotRepository.findById(1L)).thenReturn(Optional.of(parkingLot));

        assertEquals(parkingLotService.getAvailableParkingBlocks(1L).size(), parkingLot.getCapacity().intValue());
    }

    @Test
    void should_throw_not_exception_when_parking_lot_id_is_invalid() {
        when(parkingLotRepository.findById(1L)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> {
            parkingLotService.getAvailableParkingBlocks(2L);
        });
    }

    @Test
    void should_return_emptyList_when_no_available_parkingBlocks() throws NotFoundException {
        parkingLot.setParkingBlocks(buildParkingBlocks("OCCUPIED"));
        when(parkingLotRepository.findById(1L)).thenReturn(Optional.of(parkingLot));

        assertEquals(parkingLotService.getAvailableParkingBlocks(1L), emptyList());
    }

    private List<ParkingBlock> buildParkingBlocks(String status) {
        List<ParkingBlock> parkingBlockList = new ArrayList<>();
        for(int i=1; i<parkingLot.getCapacity()+1; i++)
            parkingBlockList.add(buildParkingBlock(i, status));

        return parkingBlockList;
    }

    private ParkingBlock buildParkingBlock(int position, String status) {
        ParkingBlock parkingBlock = new ParkingBlock();
        parkingBlock.setId(1L);
        parkingBlock.setStatus(status);
        parkingBlock.setPosition(position);
        return parkingBlock;
    }
}