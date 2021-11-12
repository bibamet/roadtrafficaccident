package com.example.roadtrafficaccident.exceptionbody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExceptionBody {

    private String message;
    private String debugMessage;

}
