package com.example.roadtrafficaccident.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RtaDto {

    @Valid
    private AddressDto address;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Long numberOfRta;

    @Positive
    @Min(value = 1000, message = "Серия должна состоять из 4 цифр")
    @Max(value = 9999, message = "Серия должна состоять из 4 цифр")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long serialLicense;

    @Positive
    @Min(value = 100000, message = "Номер должен состоять из 6 цифр")
    @Max(value = 999999, message = "Номер должен состоять из 6 цифр")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long numberLicense;

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
