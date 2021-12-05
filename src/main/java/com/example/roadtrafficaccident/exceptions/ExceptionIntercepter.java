package com.example.roadtrafficaccident.exceptions;

import com.example.roadtrafficaccident.dto.ApiError;
import com.example.roadtrafficaccident.dto.ApiErrorType;
import com.example.roadtrafficaccident.exceptions.parentexception.ParentException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionIntercepter {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ParentException.class})
    protected ApiError handlerParentException(ParentException ex) {
        return wrapBusinessException(ex, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    @ExceptionHandler({RestClientException.class})
    protected ApiError handleRestClient(RestClientException ex) {
        return wrapSystemException(ex.getMessage(), HttpStatus.REQUEST_TIMEOUT);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    @ExceptionHandler({WrongApiUrlException.class})
    protected ApiError handleWrongApiUrl(WrongApiUrlException ex) {
        return wrapSystemException(ex.getMessage(), HttpStatus.REQUEST_TIMEOUT);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({RTASNotFoundException.class})
    protected ApiError rtasEntityNotFoundException(RTASNotFoundException ex) {
        return wrapBusinessException(ex, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler({DataIntegrityViolationException.class})
    protected ApiError handleDataIntegrityViolation(DataIntegrityViolationException exception) {
        if (Objects.nonNull(exception.getRootCause())) {
            return wrapBusinessException(exception.getRootCause(), HttpStatus.BAD_REQUEST);
        }
        return wrapBusinessException(exception, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ApiError handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        if (exception.getBindingResult().hasErrors()) {
            List<ObjectError> errors = exception.getBindingResult().getAllErrors();
            String allErrors = errors.stream().map(error -> (FieldError) error)
                    .map(error -> error.getDefaultMessage() + ": <" + error.getField() + ">")
                    .collect(Collectors.joining("; "));
            return wrapValidException(allErrors, HttpStatus.BAD_REQUEST);
        } else {
            return wrapValidException("Validation error", HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    protected ApiError handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return wrapValidException(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler({HttpMessageNotReadableException.class})
    protected ApiError handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return wrapValidException(ex.getRootCause().getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ApiError wrapBusinessException(Throwable throwable, HttpStatus status) {
        return ApiError.builder()
                .message(throwable.getMessage())
                .status(status)
                .type(ApiErrorType.BUSINESS)
                .build();
    }

    private ApiError wrapValidException(String message, HttpStatus status) {
        return ApiError.builder()
                .message(message)
                .status(status)
                .type(ApiErrorType.VALIDATION)
                .build();
    }

    private ApiError wrapSystemException(String message, HttpStatus status) {
        return ApiError.builder()
                .message(message)
                .status(status)
                .type(ApiErrorType.SYSTEM)
                .build();
    }
}
