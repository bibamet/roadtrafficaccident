package com.example.roadtrafficaccident.exceptions.parentexception;

public class AddressNotFoundException extends ParentException {

    public AddressNotFoundException(Double longtitude, Double latitude) {
        super(String.format("По указанным координатам не удалось найти адрес места ДТП. Широта %f, долгота %f"
                    , latitude, longtitude));
        initCause(this);
    }

}
