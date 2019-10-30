package ita.tw.afs.spark.repository;

import ita.tw.afs.spark.model.ParkingBoy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingBoyRepository extends JpaRepository<ParkingBoy, Long> {
    ParkingBoy findByUsernameAndPassword(String username, String password);

    ParkingBoy findParkingBoyById(Long parkingBoyId);
}
