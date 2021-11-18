package com.example.roadtrafficaccident.service;

import com.example.roadtrafficaccident.data.LogRepo;
import com.example.roadtrafficaccident.entity.log.LogEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepo logRepo;

    public void save(LogEntity log) {
        logRepo.save(log);
    }

}
