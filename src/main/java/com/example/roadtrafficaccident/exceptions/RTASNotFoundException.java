package com.example.roadtrafficaccident.exceptions;

import javax.persistence.EntityNotFoundException;

public class RTASNotFoundException extends EntityNotFoundException {

    public RTASNotFoundException(Long numOfRTA) {
        super(String.format("Не найдено ДТП с номером авто: %d", numOfRTA));
    }

}
