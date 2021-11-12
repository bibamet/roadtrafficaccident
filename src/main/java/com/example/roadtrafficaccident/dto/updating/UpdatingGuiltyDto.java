package com.example.roadtrafficaccident.dto.updating;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatingGuiltyDto {

    private Long numberOfRta;
    private String numberOfCar;
    private boolean guilty;
    private Double penalty;

}
