package ita.tw.afs.spark.repository;

import ita.tw.afs.spark.model.ParkingBlock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingBlockRepository extends JpaRepository<ParkingBlock, Long> {
}
