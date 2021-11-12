package com.example.roadtrafficaccident.exceptions;

public class RTAEntityNotFoundException extends RuntimeException {

    public RTAEntityNotFoundException(Long numberOfRTA, String numberOfCar) {
        super(String.format("Запись ДТП с номером протокола %s и номером автомобиля %s не найдена",
                numberOfRTA, numberOfCar));
    }

}
