package ita.tw.afs.spark.repository;

import ita.tw.afs.spark.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
