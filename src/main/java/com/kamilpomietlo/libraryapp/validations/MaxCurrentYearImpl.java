package com.kamilpomietlo.libraryapp.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class MaxCurrentYearImpl implements ConstraintValidator<MaxCurrentYear, Integer> {

    public MaxCurrentYearImpl() {
    }

    @Override
    public void initialize(MaxCurrentYear constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext context) {
        int currentYear = LocalDateTime.now().getYear();

        if (year != null) {
            return (year > 0) & (year <= currentYear);
        }

        return false;
    }
}
