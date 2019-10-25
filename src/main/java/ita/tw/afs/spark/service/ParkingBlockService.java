package ita.tw.afs.spark.service;

import ita.tw.afs.spark.model.ParkingBlock;
import ita.tw.afs.spark.repository.ParkingBlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingBlockService {

    @Autowired
    private ParkingBlockRepository parkingBlockRepository;

    public List<ParkingBlock> getParkingLotSpaces(Long id) {
        return parkingBlockRepository.findAll();
    }
}
