package com.example.roadtrafficaccident.adapter.constants;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class GeocoderConstant {

    private final String[] lowerCorner;
    private final String[] upperCorner;

}
