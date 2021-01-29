package com.kamilpomietlo.libraryapp.repositories;

import com.kamilpomietlo.libraryapp.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Provides repository for {@code Book} objects.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Returns set of {@code Book} objects searched by title or containing it's part. Ignores letter case.
     *
     * @param title value to be searched by
     * @return set of books
     */
    Set<Book> findByTitleIgnoreCaseContaining(String title);
}
