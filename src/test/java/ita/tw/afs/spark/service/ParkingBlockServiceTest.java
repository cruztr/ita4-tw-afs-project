package ita.tw.afs.spark.service;

import ita.tw.afs.spark.model.Orders;
import ita.tw.afs.spark.model.ParkingBlock;
import ita.tw.afs.spark.model.ParkingLot;
import ita.tw.afs.spark.repository.ParkingBlockRepository;
import ita.tw.afs.spark.repository.ParkingLotRepository;
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

import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ParkingBlockService.class)
@ActiveProfiles(profiles = "ParkingBlockServiceTest")
class ParkingBlockServiceTest {

    @Autowired
    ParkingBlockService parkingBlockService;

    @MockBean
    ParkingLotRepository parkingLotRepository;

    @MockBean
    ParkingBlockRepository parkingBlockRepository;

    private ParkingBlock parkingBlock;

    @BeforeEach
    void setUp() {
        parkingBlock = new ParkingBlock();
        parkingBlock.setId(1L);
        parkingBlock.setStatus("OCCUPIED");
        parkingBlock.setParkingLotId(1L);
        parkingBlock.setPosition(15);
    }

    @Test
    public void should_get_all_parking_blocks() {
        when(parkingBlockRepository.findAll()).thenReturn(singletonList(parkingBlock));

        assertSame(parkingBlockService.getAll().get(0), parkingBlock);
        assertSame(parkingBlockService.getAll().get(0).getId(), 1L);
        assertSame(parkingBlockService.getAll().get(0).getStatus(), "OCCUPIED");
    }

    @Test
    void should_get_specific_parking_block() {
        when(parkingBlockRepository.findById(1L)).thenReturn(Optional.of(parkingBlock));

        assertEquals(parkingBlockService.getParkingBlock(1L), Optional.of(parkingBlock));
    }

    @Test
    void should_update_parkingBlock_status_if_parkingLot_is_present() throws NotFoundException {
        ParkingLot parkingLot = new ParkingLot(123L, "MAAX", 30);
        Orders order = new Orders();
        order.setOrderId(1L);
        order.setParkingBlockPosition(5);
        order.setParkingLotId(123L);

        when(parkingLotRepository.findById(123L)).thenReturn(Optional.of(parkingLot));
        when(parkingBlockRepository.findByParkingLotIdAndPosition(parkingLot.getId(), 5)).thenReturn(parkingBlock);
        when(parkingBlockRepository.save(parkingBlock)).thenReturn(parkingBlock);

        assertSame(parkingBlockService.updateParkingBlockStatusToOccupied(order), parkingBlock);
        assertSame(parkingBlockService.updateParkingBlockStatusToOccupied(order).getStatus(), "OCCUPIED");
    }

    @Test
    void should_update_parkingBlock_status_if_parkingLot_is_not_present() {
        Orders order = new Orders();
        order.setOrderId(1L);
        order.setParkingBlockPosition(5);
        order.setParkingLotId(123L);

        when(parkingLotRepository.findById(123L)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> {
            parkingBlockService.updateParkingBlockStatusToOccupied(order);
        });
    }

    @Test
    void should_update_parkingBlock_status_to_available() {
        Long parkingLotId = 2L;
        Integer parkingBlockPosition = 3;
        when(parkingBlockRepository.findByParkingLotIdAndPosition(parkingLotId, parkingBlockPosition))
                .thenReturn(parkingBlock);
        when(parkingBlockRepository.save(any())).thenReturn(parkingBlock);
        parkingBlockService.updateParkingBlockStatusToAvailable(parkingLotId, parkingBlockPosition);
    }
}