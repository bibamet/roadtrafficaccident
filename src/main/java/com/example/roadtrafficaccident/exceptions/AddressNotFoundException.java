package com.example.roadtrafficaccident.exceptions;

import javax.persistence.EntityNotFoundException;

public class AddressNotFoundException extends EntityNotFoundException {

    public AddressNotFoundException(Double longtitude, Double latitude) {

        super(String.format("По указанным координатам не удалось найти адрес места ДТП. Широта %f, долгота %f"
                    , latitude, longtitude));
        initCause(this);


    }

    // это исключение из пакета javax, проверь что у тебя валидно работают обработчики потому что EFE - это про persistence, а твое исключение про рест взаимодейсвтие и бизнес логику
    // как-то запутанно выходит

}
