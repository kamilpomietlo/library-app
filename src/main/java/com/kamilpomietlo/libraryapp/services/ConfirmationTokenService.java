package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.ConfirmationToken;

public interface ConfirmationTokenService extends BaseService<ConfirmationToken> {

    void saveConfirmationToken(ConfirmationToken confirmationToken);
    void findConfirmationTokenByToken(String confirmationToken);
}
