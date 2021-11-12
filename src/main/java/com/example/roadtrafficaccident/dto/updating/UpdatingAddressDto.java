package com.example.roadtrafficaccident.dto.updating;

import com.example.roadtrafficaccident.dto.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatingAddressDto {

    private Long numberOfRta;

    private String numberOfCar;

    private AddressDto address;

}
