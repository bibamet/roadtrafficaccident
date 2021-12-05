package com.example.roadtrafficaccident.exceptions.parentexception;

public class RTAEntityNotFoundException extends ParentException {

    public RTAEntityNotFoundException(Long numberOfRTA, String numberOfCar) {
        super(String.format("Запись ДТП с номером протокола %s и номером автомобиля %s не найдена",
                numberOfRTA, numberOfCar));
    }

}
