package com.example.roadtrafficaccident.dto.updating;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatingNumRtaDto {

    private Long numberOfRta;

    private Long newNumberOfRta;

}
