package ita.tw.afs.spark.repository;

import ita.tw.afs.spark.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
