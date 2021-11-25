package com.example.roadtrafficaccident.exceptions;

import javax.persistence.EntityNotFoundException;

public class RTAEntityNotFoundException extends EntityNotFoundException {

    public RTAEntityNotFoundException(Long numberOfRTA, String numberOfCar) {
        super(String.format("Запись ДТП с номером протокола %s и номером автомобиля %s не найдена",
                numberOfRTA, numberOfCar));
    }

}
