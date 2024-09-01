package com.customerManagementApi.controllers;

import com.customerManagementApi.exceptions.CustomerNotFoundException;
import com.customerManagementApi.models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException validateException) {

        List<String> errors = new ArrayList<>();
        for(FieldError error : validateException.getFieldErrors()){
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
                "BAD REQUEST", errors);
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(CustomerNotFoundException customerNotFoundException){
        List<String> errors = new ArrayList<>();
        errors.add(customerNotFoundException.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
                "NOT FOUND", errors);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponse> handleSqlExceptions(Exception ex){
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
                "DATABASE CONNECTION ISSUE", errors);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}