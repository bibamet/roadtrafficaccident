package com.example.roadtrafficaccident.exceptions;

import com.example.roadtrafficaccident.exceptionbody.ApiError;
import com.example.roadtrafficaccident.exceptionbody.ApiErrorType;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionIntercepter {


    @ExceptionHandler({AddressNotFoundException.class})
    protected ResponseEntity<ApiError> AddressEntityNotFoundException(AddressNotFoundException ex) {

        HttpStatus httpStatus = ex.isBadCoords() ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR; // скорее всего, только NOT_FOUND отдавать

        ApiError apiError = ApiError.builder()
                .message(ex.getMessage())
                .status(httpStatus)
                .type(ApiErrorType.VALIDATION)
                .build();
        return new ResponseEntity<>(apiError, ex.isBadCoords() ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR); // скорее всего, только NOT_FOUND отдавать

    }

    @ExceptionHandler({AreaNotFoundException.class})
    protected ResponseEntity<ApiError> AreaNotFoundException(AreaNotFoundException ex) {

        HttpStatus httpStatus = ex.isBadCoords() ? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR; // скорее всего, только NOT_FOUND отдавать

        ApiError apiError = ApiError.builder()
                .message(ex.getMessage())
                .type(ApiErrorType.BUSINESS)
                .status(httpStatus)
                .build();
        return new ResponseEntity<>(apiError, httpStatus);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({RTAEntityNotFoundException.class})
    protected ApiError RTAEntityNotFoundException(RTAEntityNotFoundException ex) {

        ApiError apiError = ApiError.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .type(ApiErrorType.BUSINESS)
                .build();
        return apiError;

    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({RTASNotFoundException.class})
    protected ApiError RTASEntityNotFoundException(RTASNotFoundException ex) {

        ApiError apiError = ApiError.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .type(ApiErrorType.BUSINESS)
                .build();

        return apiError;

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class})
    protected ApiError handleConstraintViolation(DataIntegrityViolationException exception) {

        if (Objects.nonNull(exception.getRootCause())) {
            return wrapBusinessException(exception.getRootCause(), HttpStatus.BAD_REQUEST);
        }
        return wrapBusinessException(exception, HttpStatus.BAD_REQUEST);

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ApiError handleConstraintException(MethodArgumentNotValidException exception) {

        if (exception.getBindingResult().hasErrors()) {
            List<ObjectError> errors = exception.getBindingResult().getAllErrors();

            String allErrors = errors.stream().map(error -> (FieldError) error)
                    .map(error -> error.getDefaultMessage() + ": <" + error.getField() + ">")
                    .collect(Collectors.joining("; "));

            return wrapValidException(allErrors, HttpStatus.BAD_REQUEST);

        } else return wrapValidException("Validation error", HttpStatus.BAD_REQUEST);

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

    private ApiError wrapSystemException(HttpStatus status) {
        return ApiError.builder()
                .status(status)
                .type(ApiErrorType.SYSTEM)
                .build();
    }

}
