package ita.tw.afs.spark.service;

import ita.tw.afs.spark.model.ParkingBlock;
import ita.tw.afs.spark.model.ParkingLot;
import ita.tw.afs.spark.repository.ParkingBlockRepository;
import ita.tw.afs.spark.repository.ParkingLotRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParkingLotService {

    private static final String AVAILABLE = "AVAILABLE";
    private static final String PARKING_LOT_NOT_FOUND = "Parking Lot not found";
    private static final String PLEASE_INPUT_ALL_REQUIRED_FIELDS = "PLEASE INPUT ALL REQUIRED FIELDS";
    private static final String INVALID_CAPACITY = "INVALID CAPACITY";
    private static final String PLEASE_INPUT_A_VALID_RATE = "PLEASE INPUT A VALID RATE";

    @Autowired
    private ParkingLotRepository parkingLotRepo;

    @Autowired
    private ParkingBlockRepository parkingBlockRepo;

    public ParkingLot saveLotAndCreateBlocks(ParkingLot parkingLot) throws NotSupportedException {
        if(!parkingLot.getName().isEmpty()
            && !parkingLot.getLocation().isEmpty()
            && parkingLot.getCapacity() != null) {
            if(parkingLot.getCapacity() > 0) {
                if(parkingLot.getRate() > 0) {
                    parkingLotRepo.save(parkingLot);
                    List<ParkingBlock> parkingBlockList = createParkingBlockList(parkingLot);

                    parkingLot.setParkingBlocks(parkingBlockList);
                    parkingLotRepo.save(parkingLot);

                    return parkingLot;
                }
                throw new NotSupportedException(PLEASE_INPUT_A_VALID_RATE);
            }
            throw new NotSupportedException(INVALID_CAPACITY);
        }
        throw new NotSupportedException(PLEASE_INPUT_ALL_REQUIRED_FIELDS);
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
            parkingBlock.setParkingLotId(parkingLot.getId());
            parkingBlockList.add(parkingBlock);
        }
        parkingBlockRepo.saveAll(parkingBlockList);
        return parkingBlockList;
    }

    public Optional<ParkingLot> getParkingLot(Long id) throws NotFoundException {
        Optional<ParkingLot> parkingLot = parkingLotRepo.findById(id);
        if(parkingLot.isPresent()){
            return parkingLot;
        }
        throw new NotFoundException(PARKING_LOT_NOT_FOUND);
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
