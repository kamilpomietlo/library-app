package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenService extends BaseService<ConfirmationToken> {

    void saveConfirmationToken(ConfirmationToken confirmationToken);
    Optional<ConfirmationToken> findConfirmationTokenByToken(String confirmationToken);
}
