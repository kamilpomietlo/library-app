package com.kamilpomietlo.libraryapp.validations;

import com.kamilpomietlo.libraryapp.services.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailNotInUseImpl implements ConstraintValidator<EmailNotInUse, String> {

    private final UserService userService;

    public EmailNotInUseImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(EmailNotInUse constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !userService.isEmailUsed(email);
    }
}
