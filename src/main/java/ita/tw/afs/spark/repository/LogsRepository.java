package ita.tw.afs.spark.repository;

import ita.tw.afs.spark.model.Logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogsRepository extends JpaRepository<Logs, Long> {
    Iterable<Logs> findByUserIdAndUserType(Long userId, String userType);
}
