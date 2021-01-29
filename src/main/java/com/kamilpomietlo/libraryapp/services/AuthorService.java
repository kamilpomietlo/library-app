package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.AuthorCommand;
import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.model.Book;

import java.util.List;

/**
 * Provides operations related to {@code Author} objects.
 */
public interface AuthorService extends BaseService<Author> {

    /**
     * Saves object.
     *
     * @param authorCommand object to be saved
     * @return saved object
     */
    AuthorCommand saveAuthorCommand(AuthorCommand authorCommand);

    /**
     * Finds the object by provided id.
     *
     * @param id object id
     * @return found object
     */
    AuthorCommand findCommandById(Long id);

    /**
     * Gets books which belong to particular {@code Author}.
     *
     * @param id object id
     * @return list of books
     */
    List<Book> getAuthorsBooks(Long id);
}
