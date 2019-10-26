package ita.tw.afs.spark.service;

import ita.tw.afs.spark.model.Orders;
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

import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
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
        parkingBlock.setStatus("OPEN");
        parkingBlock.setParkingLot(new ParkingLot());
        parkingBlock.setPosition(15);
    }

    @Test
    public void should_get_all_parking_blocks(){
        when(parkingBlockRepository.findAll()).thenReturn(singletonList(parkingBlock));

        assertSame(parkingBlockService.getAll().get(0), parkingBlock);
        assertSame(parkingBlockService.getAll().get(0).getId(), 1L);
        assertSame(parkingBlockService.getAll().get(0).getStatus(), "OPEN");
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
        when(parkingBlockRepository.findByParkingLotAndParkingBlockPosition(parkingLot, 5)).thenReturn(parkingBlock);
        when(parkingBlockRepository.save(parkingBlock)).thenReturn(parkingBlock);

        assertSame(parkingBlockService.updateParkingBlockStatus(order), parkingBlock);
        assertSame(parkingBlockService.updateParkingBlockStatus(order).getStatus(), "OCCUPIED");
    }

    @Test
    void should_update_parkingBlock_status_if_parkingLot_is_not_present() {
        Orders order = new Orders();
        order.setOrderId(1L);
        order.setParkingBlockPosition(5);
        order.setParkingLotId(123L);

        when(parkingLotRepository.findById(123L)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> {
            parkingBlockService.updateParkingBlockStatus(order);
        });

    }
}