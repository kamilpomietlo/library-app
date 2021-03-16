package com.kamilpomietlo.libraryapp.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

/**
 * {@inheritDoc}
 */
public class MaxCurrentYearImpl implements ConstraintValidator<MaxCurrentYear, Integer> {

    public MaxCurrentYearImpl() {
    }

    @Override
    public void initialize(MaxCurrentYear constraintAnnotation) {
    }

    /**
     * Checks whether value is equal to or lower than current year.
     *
     * @param year value to be checked
     * @param context validator context
     * @return boolean value
     */
    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext context) {
        int currentYear = LocalDateTime.now().getYear();

        if (year != null) {
            return (year >= 0) && (year <= currentYear);
        }

        return false;
    }
}
