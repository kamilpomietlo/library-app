package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.ConfirmationToken;
import com.kamilpomietlo.libraryapp.repositories.ConfirmationTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfirmationTokenServiceImpl extends BaseServiceImpl<ConfirmationToken, ConfirmationTokenRepository>
        implements ConfirmationTokenService {

    private final UserService userService;

    public ConfirmationTokenServiceImpl(ConfirmationTokenRepository repository, UserService userService) {
        super(repository);
        this.userService = userService;
    }

    @Override
    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        repository.save(confirmationToken);
    }

    @Override
    public void findConfirmationTokenByToken(String confirmationToken) {
        Optional<ConfirmationToken> confirmationTokenOptional = repository.findByConfirmationToken(confirmationToken);

        confirmationTokenOptional.ifPresent(userService::confirmUser);
    }
}
