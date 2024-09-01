package com.customerManagementApi.models.validators;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidDateValidator implements ConstraintValidator<ValidDate, String> {

    private String pattern;
    private String message;
    private boolean past;
    private boolean future;

    @Override
    public void initialize(ValidDate constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
        this.message = constraintAnnotation.message();
        this.past = constraintAnnotation.past();
        this.future = constraintAnnotation.future();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDate date = LocalDate.parse(value,formatter);
            if(past){
                if(date.isBefore(LocalDate.now()))
                    return true;
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message + ": Must be a past date").addConstraintViolation();
                return false;
            }else if(future){
                if(date.isAfter(LocalDate.now()))
                    return true;
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message + ": Must be a future date").addConstraintViolation();
                return false;
            }
            return true;
        } catch (DateTimeParseException e) {
            context.disableDefaultConstraintViolation();
            if(e.getCause()!=null)
                context.buildConstraintViolationWithTemplate(message + ": " + e.getCause().getMessage()).addConstraintViolation();
            else
                context.buildConstraintViolationWithTemplate(message + ". Required Format: " + pattern).addConstraintViolation();
            return false;
        }
    }
}