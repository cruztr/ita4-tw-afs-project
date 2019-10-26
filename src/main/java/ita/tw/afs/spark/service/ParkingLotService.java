package ita.tw.afs.spark.service;

import ita.tw.afs.spark.model.ParkingBlock;
import ita.tw.afs.spark.model.ParkingLot;
import ita.tw.afs.spark.repository.ParkingBlockRepository;
import ita.tw.afs.spark.repository.ParkingLotRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParkingLotService {

    private static final String AVAILABLE = "AVAILABLE";
    private static final String PARKING_LOT_NOT_FOUND = "Parking Lot not found";

    @Autowired
    private ParkingLotRepository parkingLotRepo;

    @Autowired
    private ParkingBlockRepository parkingBlockRepo;

    public ParkingLot saveLotAndCreateBlocks(ParkingLot parkingLot) {
        List<ParkingBlock> parkingBlockList = createParkingBlockList(parkingLot);
        parkingLot.setParkingBlocks(parkingBlockList);

        parkingLotRepo.save(parkingLot);
        parkingBlockRepo.saveAll(parkingBlockList);

        return parkingLot;
    }

    public List<ParkingLot> getAll() {
        return parkingLotRepo.findAll();
    }

    private List<ParkingBlock> createParkingBlockList(ParkingLot parkingLot) {
        List<ParkingBlock> parkingBlockList = new ArrayList<>();
        for(int ctr=0; ctr<parkingLot.getCapacity(); ctr++){
            ParkingBlock parkingBlock = new ParkingBlock();
            parkingBlock.setPosition(ctr);
            parkingBlock.setStatus(AVAILABLE);
            parkingBlock.setParkingLot(parkingLot);

            parkingBlockList.add(parkingBlock);
        }
        return parkingBlockList;
    }

    public Optional<ParkingLot> getParkingLot(Long id) {
        return parkingLotRepo.findById(id);
    }

    public List<ParkingBlock> getAvailableParkingBlocks(Long id) throws NotFoundException {
        Optional<ParkingLot> parkingLotOptional = parkingLotRepo.findById(id);

        if(!parkingLotOptional.isPresent())
            throw new NotFoundException(PARKING_LOT_NOT_FOUND);

        return parkingLotOptional.get()
                .getParkingBlocks()
                .stream()
                .filter(parkingBlock -> AVAILABLE.equals(parkingBlock.getStatus()))
                .collect(Collectors.toList());
    }
}
