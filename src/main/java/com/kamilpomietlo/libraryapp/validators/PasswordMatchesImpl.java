package com.kamilpomietlo.libraryapp.validators;

import com.kamilpomietlo.libraryapp.commands.UserRegisterCommand;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesImpl implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        UserRegisterCommand userRegisterCommand = (UserRegisterCommand) object;

        boolean isValid = userRegisterCommand.getPassword().equals(userRegisterCommand.getMatchingPassword());
        if (!isValid) {
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("matchingPassword").addConstraintViolation();
        }

        return isValid;
    }
}
