package com.example.roadtrafficaccident.exceptions;

import lombok.Getter;

import javax.persistence.EntityNotFoundException;

public class AddressNotFoundException extends EntityNotFoundException {

    @Getter
    boolean badCoords;

    public AddressNotFoundException(Double longtitude, Double latitude, boolean badCoords) {

        super(String.format("По указанным координатам не удалось найти адрес места ДТП. Широта %f, долгота %f"
                    , latitude, longtitude));
        this.badCoords = badCoords;

    }

}
