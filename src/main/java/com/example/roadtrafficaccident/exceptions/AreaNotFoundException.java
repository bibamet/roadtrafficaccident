package com.example.roadtrafficaccident.exceptions;

import javax.persistence.EntityNotFoundException;

public class AreaNotFoundException extends EntityNotFoundException {

    public AreaNotFoundException(String area) {

        super(String.format("Указана некорректная область: %s", area));
        initCause(this);

    }

}
