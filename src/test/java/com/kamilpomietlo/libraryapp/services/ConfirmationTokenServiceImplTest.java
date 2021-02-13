package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.ConfirmationToken;
import com.kamilpomietlo.libraryapp.repositories.ConfirmationTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConfirmationTokenServiceImplTest {

    private ConfirmationTokenService confirmationTokenService;

    @Mock
    ConfirmationTokenRepository confirmationTokenRepository;

    @BeforeEach
    void setUp() {
        confirmationTokenService = new ConfirmationTokenServiceImpl(confirmationTokenRepository);
    }

    @Test
    void deleteConfirmationTokenById() {
        // given
        Long confirmationTokenId = 1L;

        // when
        confirmationTokenRepository.deleteById(confirmationTokenId);

        // then
        verify(confirmationTokenRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void saveConfirmationToken() {
        // given
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setId(1L);

        when(confirmationTokenRepository.save(any())).thenReturn(confirmationToken);

        // when
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        // then
        verify(confirmationTokenRepository, times(1)).save(any());
    }

    @Test
    void findConfirmationTokenByToken() {
        // given
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setId(1L);
        confirmationToken.setConfirmationToken("123");

        Optional<ConfirmationToken> confirmationTokenOptional = Optional.of(confirmationToken);

        when(confirmationTokenRepository.findByConfirmationToken(anyString())).thenReturn(confirmationTokenOptional);

        // when
        confirmationTokenService.findConfirmationTokenByToken(confirmationToken.getConfirmationToken());

        // then
        verify(confirmationTokenRepository, times(1)).findByConfirmationToken(anyString());
    }
}
