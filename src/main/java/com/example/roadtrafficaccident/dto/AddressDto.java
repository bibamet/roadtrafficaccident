package com.example.roadtrafficaccident.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Double longtitude;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Double latitude;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String addressView;

}
