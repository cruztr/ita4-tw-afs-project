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
    private static final String AVAILABLE = "AVAILABLE";
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

    public ParkingBlock updateParkingBlockStatusToOccupied(Orders orders) throws NotFoundException {
        ParkingBlock parkingBlock;
        Optional<ParkingLot> parkingLotOptional = parkingLotRepository.findById(orders.getParkingLotId());
        if(parkingLotOptional==null || !parkingLotOptional.isPresent())
            throw new NotFoundException(PARKING_LOT_NOT_FOUND);
        parkingBlock = parkingBlockRepository.findByParkingLotIdAndPosition(parkingLotOptional.get().getId(), orders.getParkingBlockPosition());
        parkingBlock.setStatus(OCCUPIED);
        return parkingBlockRepository.save(parkingBlock);
    }

    public void updateParkingBlockStatusToAvailable(Long parkingLotId, Integer parkingBlockPosition) {
        ParkingBlock parkingBlock = parkingBlockRepository.findByParkingLotIdAndPosition(parkingLotId, parkingBlockPosition);
        parkingBlock.setStatus(AVAILABLE);
        parkingBlockRepository.save(parkingBlock);
    }
}
