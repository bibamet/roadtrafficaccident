package com.example.roadtrafficaccident.dto.getting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GettingRtaByCar {

    private String numberOfCar;

    private LocalDateTime from;

    private LocalDateTime to;

}
