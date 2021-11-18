package com.example.roadtrafficaccident.data;

import com.example.roadtrafficaccident.entity.log.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LogRepo extends JpaRepository<LogEntity, UUID> {
}
