package ita.tw.afs.spark.repository;

import ita.tw.afs.spark.model.ParkingBlock;
import ita.tw.afs.spark.model.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingBlockRepository extends JpaRepository<ParkingBlock, Long> {
    ParkingBlock findByParkingLotAndParkingBlockPosition(ParkingLot parkingLot, Integer parkingBlockPosition);
}
