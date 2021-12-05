package com.example.roadtrafficaccident.dto.updating;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatingGuiltyDto {

    @Positive
    @NotNull
    private Long numberOfRta;

    @NotBlank
    @Pattern(regexp = "^[АВЕКМНОРСТУХ]\\d{3}(?<!000)[АВЕКМНОРСТУХ]{2}\\d{2,3}$", message = "" +
            "Должно соответветстовать формату гос. знака РФ. А123БВ32")
    private String numberOfCar;

    @NotNull
    private boolean guilty;

    private Double penalty;

}
