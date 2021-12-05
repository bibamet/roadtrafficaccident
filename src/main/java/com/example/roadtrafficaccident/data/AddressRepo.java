package com.example.roadtrafficaccident.data;

import com.example.roadtrafficaccident.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepo extends JpaRepository<AddressEntity, Long> {
    @Query(value = "SELECT address FROM address address WHERE address.longtitude >= :lowerLongtitude and address.longtitude <= :upperLongtitude" +
            " AND address.latitude >= :lowerLatitude AND address.latitude <= :upperLatitude")
    List<AddressEntity> findAddresses(Double lowerLongtitude, Double upperLongtitude, Double lowerLatitude, Double upperLatitude);
    Optional<AddressEntity> findByLongtitudeAndLatitude(Double longtitude, Double latitude);
}
