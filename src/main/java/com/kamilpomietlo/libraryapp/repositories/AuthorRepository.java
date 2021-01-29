package com.kamilpomietlo.libraryapp.repositories;

import com.kamilpomietlo.libraryapp.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Provides repository for {@code Author} objects.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    /**
     * Returns set of {@code Author} objects searched by name or containing it's part. Ignores letter case.
     *
     * @param name value to be searched by
     * @return set of authors
     */
    Set<Author> findByNameIgnoreCaseContaining(String name);
}
