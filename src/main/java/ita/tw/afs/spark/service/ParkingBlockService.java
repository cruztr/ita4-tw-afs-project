package ita.tw.afs.spark.service;

import ita.tw.afs.spark.model.Orders;
import ita.tw.afs.spark.model.ParkingBlock;
import ita.tw.afs.spark.model.ParkingLot;
import ita.tw.afs.spark.repository.ParkingBlockRepository;
import ita.tw.afs.spark.repository.ParkingLotRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingBlockService {

    private static final String OCCUPIED = "OCCUPIED";
    private static final String PARKING_LOT_NOT_FOUND = "Parking Lot Not Found";

    @Autowired
    private ParkingBlockRepository parkingBlockRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public List<ParkingBlock> getAll() {
        return parkingBlockRepository.findAll();
    }

    public Optional<ParkingBlock> getParkingBlock(Long id) {
        return parkingBlockRepository.findById(id);
    }

    public ParkingBlock updateParkingBlockStatus(Orders order) throws NotFoundException {
        ParkingBlock parkingBlock;
        Optional<ParkingLot> parkingLotOptional = parkingLotRepository.findById(order.getParkingLotId());

        if(parkingLotOptional==null || !parkingLotOptional.isPresent())
            throw new NotFoundException(PARKING_LOT_NOT_FOUND);

        parkingBlock = parkingBlockRepository.findByParkingLotAndParkingBlockPosition(parkingLotOptional.get(), order.getParkingBlockPosition());
        parkingBlock.setStatus(OCCUPIED);
        return parkingBlockRepository.save(parkingBlock);
    }
}
