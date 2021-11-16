package com.example.roadtrafficaccident.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverDTO {

    private String name;
    private String category;
    private LocalDate license;
    private String numberOfCar;
    private Long serial_license;
    private Long number_license;

}
