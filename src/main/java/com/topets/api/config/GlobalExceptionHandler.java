package com.topets.api.config;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.topets.api.domain.dto.DataErrorResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<DataErrorResponse> handleIllegalArgumentException(IllegalArgumentException e){
        List<String> details = new ArrayList<>();
        details.add(e.getMessage());

        DataErrorResponse error = new DataErrorResponse("Invalid argument", details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<DataErrorResponse> handleEntityNotFoundException(NoSuchElementException e){
        List<String> details = new ArrayList<>();
        details.add(e.getMessage());
        DataErrorResponse error = new DataErrorResponse("Not found", details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DataErrorResponse> handleException(Exception e){
        List<String> details = new ArrayList<>();
        details.add(e.getMessage());

        DataErrorResponse error = new DataErrorResponse("Error", details);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<DataErrorResponse> handleJsonParseException(HttpMessageNotReadableException e) {

        List<String> details = new ArrayList<>();
        // *** extract the root cause of the error ***
        Throwable rootCause = e.getRootCause();
        // *** checks if the cause of the error is an instance of InvalidFormatException ***
        // *** InvalidFormatException is normally thrown for a specific type such as enum ***
        if (rootCause instanceof InvalidFormatException formatException) {
            // *** if the type is enum it enters the condition ***
            if (formatException.getTargetType().isEnum()) {

                // *** get the field that caused the problema -> the last of the list ***
                String fieldName = formatException.getPath().get(formatException.getPath().size() - 1).getFieldName();
                String invalidValue = formatException.getValue().toString();
                String allowedValues = Arrays.toString(formatException.getTargetType().getEnumConstants());

                String errorMessage = String.format("Invalid value for field '%s': '%s'. Allowed values are: %s",
                        fieldName, invalidValue, allowedValues);
                details.add(errorMessage);
            } else {
                details.add("Invalid request format: " + formatException.getOriginalMessage());
            }
        } else {
            details.add(e.getMessage());
        }

        DataErrorResponse error = new DataErrorResponse("Invalid Request", details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DataErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errorDetails = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        DataErrorResponse errorResponse = new DataErrorResponse("Validation Failed", errorDetails);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
