package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.ConfirmationToken;
import com.kamilpomietlo.libraryapp.repositories.ConfirmationTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * {@inheritDoc}
 */
@Service
public class ConfirmationTokenServiceImpl extends BaseServiceImpl<ConfirmationToken, ConfirmationTokenRepository>
        implements ConfirmationTokenService {

    public ConfirmationTokenServiceImpl(ConfirmationTokenRepository repository) {
        super(repository);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        repository.save(confirmationToken);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ConfirmationToken> findConfirmationTokenByToken(String confirmationToken) {
        return repository.findByConfirmationToken(confirmationToken);
    }
}
