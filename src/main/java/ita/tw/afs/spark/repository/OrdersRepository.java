package ita.tw.afs.spark.repository;

import ita.tw.afs.spark.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    Optional<Orders> getOrderByParkingLotIdAndParkingBlockPositionAndStatus(Long parkingLotId, Integer parkingBlockPosition, String status);


    Optional<Orders> findByParkingBlockPositionAndParkingLotIdAndStatus(Integer parkingBlockPosition, Long parkingLotId, String open);

    Iterable<Orders> findByStatus(String status);

    List<Orders> getOrdersByCreatedBy(Long parkingBoyId);

    List<Orders> getOrdersByClosedBy(Long parkingBoyId);
}
