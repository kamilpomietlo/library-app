package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.model.Genre;

import java.util.Set;

/**
 * Provides operations related to the index page.
 */
public interface IndexService {

    /**
     * Gets set of books from database depending on the info provided by the user.
     *
     * @param book book object
     * @param author author object
     * @param genre genre enum value
     * @return set of books
     */
    Set<Book> findBooks(Book book, Author author, Genre genre);
}
