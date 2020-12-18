package com.kamilpomietlo.libraryapp.validations;

import com.kamilpomietlo.libraryapp.commands.UserCommand;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesImpl implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        UserCommand userCommand = (UserCommand) object;

        boolean isValid = userCommand.getPassword().equals(userCommand.getMatchingPassword());
        if (!isValid) {
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("matchingPassword").addConstraintViolation();
        }

        return isValid;
    }
}
