package ita.tw.afs.spark.repository;

import ita.tw.afs.spark.model.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
}
