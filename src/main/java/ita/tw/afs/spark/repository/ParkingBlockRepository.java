package ita.tw.afs.spark.repository;

import ita.tw.afs.spark.model.ParkingBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingBlockRepository extends JpaRepository<ParkingBlock, Long> {
    ParkingBlock findByParkingLotIdAndPosition(Long parkingLotId, Integer parkingBlockPosition);

    ParkingBlock findByParkingLotIdAndPositionAndStatus(Long parkingLotId, Integer parkingBlockPosition, String status);
}
