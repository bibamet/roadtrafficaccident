package com.example.roadtrafficaccident.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverDTO {

    private String name;
    private String category;
    private LocalDate license;
    private String numberOfCar;
    private Long serialLicense;
    private Long numberLicense;

}
