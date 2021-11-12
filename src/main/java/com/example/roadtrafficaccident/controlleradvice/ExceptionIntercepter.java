package com.example.roadtrafficaccident.controlleradvice;

import com.example.roadtrafficaccident.exceptionbody.ExceptionBody;
import com.example.roadtrafficaccident.exceptions.AddressNotFoundException;
import com.example.roadtrafficaccident.exceptions.AreaNotFoundException;
import com.example.roadtrafficaccident.exceptions.RTAEntityNotFoundException;
import com.example.roadtrafficaccident.exceptions.RTASNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionIntercepter {

    @ExceptionHandler({AddressNotFoundException.class})
    protected ResponseEntity<Object> AddressEntityNotFoundException(AddressNotFoundException ex, WebRequest request) {
        ExceptionBody exceptionBody = new ExceptionBody("Entity not found", ex.getMessage());
        return new ResponseEntity<>(exceptionBody, ex.isBadCoords() ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({AreaNotFoundException.class})
    protected ResponseEntity<Object> AreaNotFoundException(AreaNotFoundException ex, WebRequest request) {
        ExceptionBody exceptionBody = new ExceptionBody("Entity not found", ex.getMessage());
        return new ResponseEntity<>(exceptionBody, ex.isBadCoords() ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({RTAEntityNotFoundException.class})
    protected ResponseEntity<Object> RTAEntityNotFoundException(RTAEntityNotFoundException ex, WebRequest request) {
        ExceptionBody exceptionBody = new ExceptionBody("Entity not found", ex.getMessage());
        return new ResponseEntity<>(exceptionBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({RTASNotFoundException.class})
    protected ResponseEntity<Object> RTASEntityNotFoundException(RTASNotFoundException ex, WebRequest request) {
        ExceptionBody exceptionBody = new ExceptionBody("Entity not found", ex.getMessage());
        return new ResponseEntity<>(exceptionBody, HttpStatus.NOT_FOUND);
    }

}
