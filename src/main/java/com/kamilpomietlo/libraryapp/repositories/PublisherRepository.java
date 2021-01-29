package com.kamilpomietlo.libraryapp.repositories;

import com.kamilpomietlo.libraryapp.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Provides repository for {@code Publisher} objects.
 */
@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
