package com.example.roadtrafficaccident.dto.getting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GettingRtaByArea {

    @NotBlank
    private String geocode;

    private LocalDateTime from;

    private LocalDateTime to;

    @Positive
    @Min(value = 1, message = "Период получения статистики min = 1")
    private long ageCount;

}
