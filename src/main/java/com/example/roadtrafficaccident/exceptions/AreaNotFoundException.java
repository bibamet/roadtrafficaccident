package com.example.roadtrafficaccident.exceptions;

import lombok.Getter;

public class AreaNotFoundException extends RuntimeException {

    @Getter
    boolean badCoords;

    public AreaNotFoundException(String area, boolean badCoords) {

        super(String.format("Указана некорректная область: %s", area));
        this.badCoords = badCoords;

    }

}
