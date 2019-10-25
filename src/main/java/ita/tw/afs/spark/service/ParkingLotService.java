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

    @Autowired
    private ParkingLotRepository parkingLotRepo;

    @Autowired
    private ParkingBlockRepository parkingBlockRepo;

    public ParkingLot save(ParkingLot parkingLot) {
        List<ParkingBlock> parkingBlockList = new ArrayList<>();
        for(int ctr = 0; ctr< parkingLot.getCapacity(); ctr++){
            ParkingBlock parkingBlock = new ParkingBlock();
            parkingBlock.setPosition(ctr);
            parkingBlock.setStatus("Available");
            parkingBlock.setParkingLot(parkingLot);
            parkingBlockList.add(parkingBlock);
        }

        parkingLot.setParkingBlocks(parkingBlockList);
        parkingLotRepo.save(parkingLot);
        parkingBlockRepo.saveAll(parkingBlockList);

        return parkingLot;
    }
}
