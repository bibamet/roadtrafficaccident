package com.example.roadtrafficaccident.dto.updating;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatingNumRtaDto {

    @Positive
    @NotNull
    private Long numberOfRta;

    @Positive
    @NotNull
    private Long newNumberOfRta;

}
