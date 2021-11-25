package com.example.roadtrafficaccident.exceptions;

import javax.persistence.EntityNotFoundException;

public class AddressNotFoundException extends EntityNotFoundException {

    public AddressNotFoundException(Double longtitude, Double latitude) {

        super(String.format("По указанным координатам не удалось найти адрес места ДТП. Широта %f, долгота %f"
                    , latitude, longtitude));
        initCause(this);


    }

}
