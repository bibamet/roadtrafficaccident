package com.example.roadtrafficaccident.data;

import com.example.roadtrafficaccident.entity.log.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LogRepo extends JpaRepository<LogEntity, UUID> {
}
