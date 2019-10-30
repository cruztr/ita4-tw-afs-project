package ita.tw.afs.spark.repository;

import ita.tw.afs.spark.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByStatus(String status);

    Reservation findOneByReservationNumber(Long reservationNumber);

    Optional<Reservation> findByCarOwnerIdAndStatus(Long carOwnerId, String status);

    List<Reservation> getReservationsByStatus(String reserved);

}
