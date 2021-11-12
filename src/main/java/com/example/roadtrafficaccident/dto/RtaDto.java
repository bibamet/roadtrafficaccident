package com.example.roadtrafficaccident.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RtaDto {

    private String address;

    private Long numberOfRta;

    private Long serial_license;

    private Long number_license;

    private String numberOfCar;

    private LocalDateTime timeOfAccident;

    private Boolean guilty;

    private Double penalty;

}
