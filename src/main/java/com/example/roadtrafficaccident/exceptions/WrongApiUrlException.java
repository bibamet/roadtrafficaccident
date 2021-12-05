package com.example.roadtrafficaccident.exceptions;

public class WrongApiUrlException extends RuntimeException {
    public WrongApiUrlException(String message) {
        super(message);
    }
}
