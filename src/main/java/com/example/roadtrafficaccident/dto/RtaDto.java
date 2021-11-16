package com.example.roadtrafficaccident.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class RtaDto {

    @Valid
    private AddressDto address;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Long numberOfRta;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long serial_license;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long number_license;

    @NotBlank
    @Pattern(regexp = "^[АВЕКМНОРСТУХ]\\d{3}(?<!000)[АВЕКМНОРСТУХ]{2}\\d{2,3}$", message = "" +
            "Должно соответветстовать формату гос. знака РФ. А123БВ32")
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String numberOfCar;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private LocalDateTime timeOfAccident;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean guilty;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double penalty;

}
