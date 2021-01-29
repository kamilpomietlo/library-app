package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.ConfirmationToken;

import java.util.Optional;

/**
 * Provides operations related to {@code ConfirmationToken} objects.
 */
public interface ConfirmationTokenService extends BaseService<ConfirmationToken> {

    /**
     * Saves object.
     *
     * @param confirmationToken object to be saved
     */
    void saveConfirmationToken(ConfirmationToken confirmationToken);

    /**
     * Gets optional of {@code ConfirmationToken} from database.
     *
     * @param confirmationToken token description
     * @return optional of confirmation token
     */
    Optional<ConfirmationToken> findConfirmationTokenByToken(String confirmationToken);
}
