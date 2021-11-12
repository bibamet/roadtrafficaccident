package com.example.roadtrafficaccident.dto.getting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GettingRtaByArea {

    private String geocode;

    private LocalDateTime from;

    private LocalDateTime to;

    private long ageCount;

}
