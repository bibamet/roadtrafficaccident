package com.example.roadtrafficaccident.exceptions;

public class RTASNotFoundException extends RuntimeException {

    public RTASNotFoundException(Long numOfRTA) {
        super(String.format("Не найдено ДТП с номером авто: %d", numOfRTA));
    }

}
