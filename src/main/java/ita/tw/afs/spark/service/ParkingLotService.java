package ita.tw.afs.spark.service;

import ita.tw.afs.spark.dto.ParkingLotResponse;
import ita.tw.afs.spark.mapper.ParkingLotMapper;
import ita.tw.afs.spark.model.ParkingBlock;
import ita.tw.afs.spark.model.ParkingLot;
import ita.tw.afs.spark.model.Reservation;
import ita.tw.afs.spark.repository.ParkingBlockRepository;
import ita.tw.afs.spark.repository.ParkingLotRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.x509.AVA;

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
    private static final String PARKING_BLOCK_NOT_FOUND = "PARKING BLOCK NOT FOUND";
    private static final String PARKING_BLOCK_IS_ALREADY_OCCUPIED = "PARKING BLOCK IS ALREADY OCCUPIED";
    private static final String AVAILABLE1 = "AVAILABLE";
    private static final String RESERVED = "RESERVED";
    private static final String FULL = "FULL";

    @Autowired
    private ParkingLotRepository parkingLotRepo;

    @Autowired
    private ParkingBlockRepository parkingBlockRepo;

    public ParkingLot saveLotAndCreateBlocks(ParkingLot parkingLot) throws NotFoundException {
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
                throw new NotFoundException(PLEASE_INPUT_A_VALID_RATE);
            }
            throw new NotFoundException(INVALID_CAPACITY);
        }
        throw new NotFoundException(PLEASE_INPUT_ALL_REQUIRED_FIELDS);
    }

    public List<ParkingLot> getAll() {
        return parkingLotRepo.findAll();
    }

    public List<ParkingLotResponse> getAvailableParkingLots() {
        List<ParkingLotResponse> parkingLotResponses = new ArrayList<>();

        List<ParkingLot> availableParkingLots = parkingLotRepo.findAll()
                .stream().filter(parkingLot -> parkingLotHasAvailableSpace(parkingLot))
                .collect(Collectors.toList());

        for (ParkingLot parkingLot: availableParkingLots) {
            ParkingLotMapper parkingLotMapper = new ParkingLotMapper(parkingLot);
            parkingLotResponses.add(parkingLotMapper.mappedResponse(null));
        }
        return parkingLotResponses;
    }

    private Boolean parkingLotHasAvailableSpace(ParkingLot parkingLot){
        List<ParkingBlock> parkingBlocks = parkingLot.getParkingBlocks();
        List<ParkingBlock> availableBlocks = parkingBlocks.stream()
                .filter(parkingBlock -> (parkingBlock.getStatus().equals(AVAILABLE)))
                .collect(Collectors.toList());
        return availableBlocks.size() >= 1;
    }

    private List<ParkingBlock> createParkingBlockList(ParkingLot parkingLot) {
        List<ParkingBlock> parkingBlockList = new ArrayList<>();
        for(int ctr=0; ctr<parkingLot.getCapacity(); ctr++){
            ParkingBlock parkingBlock = new ParkingBlock();
            parkingBlock.setPosition(ctr + 1);
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

    public Boolean checkIfParkingBlockPositionIsValid(Long parkingLotId, Integer parkingBlockPosition, Optional<Reservation> reservation) throws NotFoundException {
        ParkingBlock parkingBlock = parkingBlockRepo.findByParkingLotIdAndPosition(parkingLotId, parkingBlockPosition);

        if(parkingBlock != null) {
            if(parkingBlock.getStatus().equals(AVAILABLE)){
                return true;
            } else if (parkingBlock.getStatus().equals(RESERVED) && reservation.isPresent()){
                return true;
            }

            throw new NotFoundException(PARKING_BLOCK_IS_ALREADY_OCCUPIED);
        }

        throw new NotFoundException(PARKING_BLOCK_NOT_FOUND);
    }

    public List<ParkingLotResponse> getParkingLotsWithStatus() {
        List<ParkingLotResponse> parkingLotResponses = new ArrayList<>();
        List<ParkingLot> parkingLotList = parkingLotRepo.findAll();

        for (ParkingLot parkingLot: parkingLotList) {
            ParkingLotMapper parkingLotMapper = new ParkingLotMapper(parkingLot);
            String status = "";
            for(ParkingBlock parkingBlock : parkingLot.getParkingBlocks()){
                if(parkingBlock.getStatus().equals(AVAILABLE)) {
                    status = AVAILABLE;
                    break;
                }
                status = FULL;
            }
            parkingLotResponses.add(parkingLotMapper.mappedResponse(status));
        }
        return parkingLotResponses;
    }
}
