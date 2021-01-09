package com.kamilpomietlo.libraryapp.validations;

import com.kamilpomietlo.libraryapp.commands.UserCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class PasswordMatchesImplTest {

    private PasswordMatchesImpl passwordMatchesImpl;
    private ConstraintValidatorContext constraintValidatorContext;

    @BeforeEach
    void setUp() {
        passwordMatchesImpl = new PasswordMatchesImpl();
    }

    @Test
    void isValid() {
        // given
        UserCommand userCommand = new UserCommand();
        userCommand.setPassword("123");
        userCommand.setMatchingPassword("123");

        // when
        boolean isValid = passwordMatchesImpl.isValid(userCommand, constraintValidatorContext);

        // then
        assertTrue(isValid);
    }
}
