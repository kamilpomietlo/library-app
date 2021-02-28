package com.kamilpomietlo.libraryapp.validations;

import com.kamilpomietlo.libraryapp.model.User;
import com.kamilpomietlo.libraryapp.repositories.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

/**
 * {@inheritDoc}
 */
public class EmailNotInUseImpl implements ConstraintValidator<EmailNotInUse, String> {

    private final UserRepository userRepository;

    public EmailNotInUseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(EmailNotInUse constraintAnnotation) {
    }

    /**
     * Checks whether the value is found in database.
     *
     * @param email value to be checked
     * @param context validator context
     * @return boolean value
     */
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !isEmailUsed(email);
    }

    private boolean isEmailUsed(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        return userOptional.isPresent();
    }
}
