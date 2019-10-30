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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
    private List<ParkingLot> parkingLotList;
    private ParkingBlock parkingBlock;
    private Optional<Orders> order;

    @BeforeEach
    void setUp() {
        List<ParkingBlock> parkingBlockList = new ArrayList<>();
        parkingLotList = new ArrayList<>();
        parkingLot = new ParkingLot(1L, "ParkingLot1", 3);
        parkingLot.setRate(10.00);
        parkingLot.setLocation("Mnl");


        parkingBlock = new ParkingBlock();
        parkingBlock.setId(1L);
        parkingBlock.setStatus("AVAILABLE");
        parkingBlock.setParkingLotId(1L);
        parkingBlock.setPosition(15);

        parkingBlockList.add(parkingBlock);
        parkingLot.setParkingBlocks(parkingBlockList);

        parkingLotList.add(parkingLot);
        order = Optional.of(new Orders());
    }

    @Test
    void should_save_lot_and_create_blocks() throws NotFoundException {
        when(parkingLotRepository.save(any())).thenReturn(parkingLot);

        assertSame(parkingLotService.saveLotAndCreateBlocks(parkingLot), parkingLot);
    }

    @Test
    void should_not_save_parking_lot_when_some_fields_are_empty() {
        parkingLot.setLocation("");
        when(parkingLotRepository.save(any())).thenReturn(parkingLot);

        assertThrows(NotFoundException.class, () -> {
            parkingLotService.saveLotAndCreateBlocks(parkingLot);
        });
    }

    @Test
    void should_not_save_parking_lot_when_capacity_is_invalid() {
        parkingLot.setCapacity(-1);
        when(parkingLotRepository.save(any())).thenReturn(parkingLot);

        assertThrows(NotFoundException.class, () -> {
            parkingLotService.saveLotAndCreateBlocks(parkingLot);
        });
    }

    @Test
    void should_not_save_parking_lot_when_rate_is_invalid() {
        parkingLot.setRate(-1.00);
        when(parkingLotRepository.save(any())).thenReturn(parkingLot);

        assertThrows(NotFoundException.class, () -> {
            parkingLotService.saveLotAndCreateBlocks(parkingLot);
        });
    }

    @Test
    void should_get_all_parking_lots(){
        when(parkingLotRepository.findAll()).thenReturn(parkingLotList);

        assertEquals(parkingLotService.getAll(), parkingLotList);
    }

    @Test
    void should_get_parking_lot_by_id() throws NotFoundException {
        Optional<ParkingLot> parkingLotOptional = Optional.of(parkingLot);
        when(parkingLotRepository.findById(1L)).thenReturn(Optional.of(parkingLot));

        assertEquals(parkingLotService.getParkingLot(1L).get(), parkingLotOptional.get());
    }

    @Test
    void should_not_get_parking_lot_when_id_doesnt_exist() {
        Optional<ParkingLot> parkingLotOptional = Optional.of(parkingLot);
        when(parkingLotRepository.findById(1L)).thenReturn(Optional.of(parkingLot));

        assertThrows(NotFoundException.class, () -> {
            parkingLotService.getParkingLot(2L);
        });
    }

    @Test
    void should_return_true_if_parking_block_status_is_available() throws NotFoundException {


        Long parkingLotId = 2L;
        Integer parkingBlockPosition = 3;
        when(parkingBlockRepository.findByParkingLotIdAndPosition(parkingLotId, parkingBlockPosition))
                .thenReturn(parkingBlock);

        assertTrue(parkingLotService.checkIfParkingBlockPositionIsValid(parkingLotId, parkingBlockPosition, order.get().getReservation()));
    }

    @Test
    void should_throw_exception_if_parking_block_status_is_occupied() {
        Long parkingLotId = 2L;
        Integer parkingBlockPosition = 3;
        parkingBlock.setStatus("OCCUPIED");
        when(parkingBlockRepository.findByParkingLotIdAndPosition(parkingLotId, parkingBlockPosition))
                .thenReturn(parkingBlock);

        assertThrows(NotFoundException.class, () -> {
            parkingLotService.checkIfParkingBlockPositionIsValid(parkingLotId, parkingBlockPosition, order.get().getReservation());
        });
    }

    @Test
    void should_throw_exception_if_parking_lot_id_is_invalid() {
        Long parkingLotId = 2L;
        Integer parkingBlockPosition = 3;
        parkingBlock.setStatus("OCCUPIED");
        when(parkingBlockRepository.findByParkingLotIdAndPosition(parkingLotId, parkingBlockPosition))
                .thenReturn(parkingBlock);

        assertThrows(NotFoundException.class, () -> {
            parkingLotService.checkIfParkingBlockPositionIsValid(3L, parkingBlockPosition, order.get().getReservation());
        });
    }

//    @Test
//    void should_get_all_parking_lot_with_available_parking_block_space() {
//        when(parkingLotRepository.findAll()).thenReturn(parkingLotList);
//        List<ParkingLot> availableParkingLots = parkingLotService.getAvailableParkingLots();
//        Assert.assertThat(parkingLotList, is(availableParkingLots));
//    }
}