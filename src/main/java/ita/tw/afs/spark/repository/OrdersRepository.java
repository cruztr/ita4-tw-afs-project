package ita.tw.afs.spark.repository;

import ita.tw.afs.spark.model.Orders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    Optional<Orders> findByOrderIdAndCreatedBy(Long orderId, Long createdBy);


    Iterable<Orders> findAllByCreatedBy(Long createdBy);
}
