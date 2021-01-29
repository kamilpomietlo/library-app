package com.kamilpomietlo.libraryapp.repositories;

import com.kamilpomietlo.libraryapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Provides repository for {@code User} objects.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Returns {@code Optional} of {@code User} object found by email.
     *
     * @param email value to be searched by
     * @return Optional of User
     */
    Optional<User> findByEmail(String email);
}
