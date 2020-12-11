package com.kamilpomietlo.libraryapp.repositories;

import com.kamilpomietlo.libraryapp.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByConfirmationToken(String token);
}