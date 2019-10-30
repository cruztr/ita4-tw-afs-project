package ita.tw.afs.spark.repository;

import ita.tw.afs.spark.model.CarOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarOwnerRepository extends JpaRepository<CarOwner, Long> {
    CarOwner findByUsernameAndPassword(String username, String password);

    CarOwner findByUsername(String username);

    CarOwner findCarOwnerById(Long id);
}
