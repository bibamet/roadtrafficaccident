package com.example.roadtrafficaccident.exceptions.parentexception;

public class AreaNotFoundException extends ParentException {

    public AreaNotFoundException(String area) {
        super(String.format("Указана некорректная область: %s", area));
        initCause(this);
    }

}
