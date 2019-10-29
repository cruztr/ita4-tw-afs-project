package ita.tw.afs.spark.repository;

import ita.tw.afs.spark.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    Optional<Orders> findByParkingBlockPositionAndParkingLotId(Integer parkingBlockPosition, Long parkingLotId);

    Optional<Orders> findByParkingBlockPositionAndParkingLotIdAndStatus(Integer parkingBlockPosition, Long parkingLotId, String open);

    Iterable<Orders> findByStatus(String status);
}
