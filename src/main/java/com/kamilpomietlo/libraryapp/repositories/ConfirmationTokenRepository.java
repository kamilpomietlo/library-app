package com.kamilpomietlo.libraryapp.repositories;

import com.kamilpomietlo.libraryapp.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Provides repository for {@code ConfirmationToken} objects.
 */
@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    /**
     * Returns {@code Optional} of {@code ConfirmationToken} object searched by token description.
     *
     * @param token value to be searched by
     * @return Optional of ConfirmationToken
     */
    Optional<ConfirmationToken> findByConfirmationToken(String token);
}
