package ita.tw.afs.spark.service;

import ita.tw.afs.spark.model.ParkingBlock;
import ita.tw.afs.spark.model.ParkingLot;
import ita.tw.afs.spark.repository.ParkingBlockRepository;
import ita.tw.afs.spark.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingLotService {

    private static final String AVAILABLE = "Available";

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
}
