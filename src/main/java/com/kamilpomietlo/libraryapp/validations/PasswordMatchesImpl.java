package com.kamilpomietlo.libraryapp.validations;

import com.kamilpomietlo.libraryapp.commands.UserCommand;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * {@inheritDoc}
 */
public class PasswordMatchesImpl implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    /**
     * Checks whether password and matchingPassword fields' values of provided object are equal.
     *
     * @param object object which fields need to be checked
     * @param context validator context
     * @return boolean value
     */
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
