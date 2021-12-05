package com.example.roadtrafficaccident.data;

import com.example.roadtrafficaccident.entity.AddressEntity;
import com.example.roadtrafficaccident.entity.RTAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RTARepo extends JpaRepository<RTAEntity, Long> {
    Optional<RTAEntity> getByNumberOfCarAndNumberOfRta(String numberOfCar, Long numberOfRTA);
    List<RTAEntity> findByNumberOfRta(Long numOfRTA);
    List<RTAEntity> findByNumberOfCarAndTimeOfAccidentBetweenOrderById(String numberOfCar, LocalDateTime from, LocalDateTime to);

    //вынести в константы мейби?
    @Query(value = "SELECT CASE WHEN count(DISTINCT rta.numberOfRta) IS NULL THEN 0 ELSE count(DISTINCT rta.numberOfRta) END as distinct_rta FROM rta rta " +
            "WHERE rta.address in :address AND rta.timeOfAccident between :from AND :to")
    int findByAddressInAndTimeOfAccidentBetween(List<AddressEntity> address, LocalDateTime from, LocalDateTime to);

}
