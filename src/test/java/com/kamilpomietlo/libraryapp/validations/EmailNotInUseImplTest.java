package com.kamilpomietlo.libraryapp.validations;

import com.kamilpomietlo.libraryapp.model.User;
import com.kamilpomietlo.libraryapp.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailNotInUseImplTest {

    @InjectMocks
    private EmailNotInUseImpl emailNotInUseImpl;

    private ConstraintValidatorContext constraintValidatorContext;

    @Mock
    UserRepository userRepository;

    @Test
    void isValid() {
        // given
        User user = new User();
        user.setEmail("qwe@example.com");

        // when
        boolean isValid = emailNotInUseImpl.isValid(user.getEmail(), constraintValidatorContext);

        // then
        assertTrue(isValid);
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void isNotValid() {
        // given
        User user = new User();
        user.setEmail("qwe@example.com");

        Optional<User> userOptional = Optional.of(user);

        when(userRepository.findByEmail(anyString())).thenReturn(userOptional);

        // when
        boolean isValid = emailNotInUseImpl.isValid(user.getEmail(), constraintValidatorContext);

        // then
        assertFalse(isValid);
        verify(userRepository, times(1)).findByEmail(anyString());
    }
}
