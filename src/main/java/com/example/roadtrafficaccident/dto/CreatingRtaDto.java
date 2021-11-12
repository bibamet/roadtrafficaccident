package com.example.roadtrafficaccident.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatingRtaDto {

    private AddressDto address;

    private Long numberOfRta;

    private String numberOfCar;

    private LocalDateTime timeOfAccident;


}
