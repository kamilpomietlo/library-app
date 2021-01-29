package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.PublisherCommand;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.model.Publisher;

import java.util.List;

/**
 * Provides operations related to {@code Publisher} objects.
 */
public interface PublisherService extends BaseService<Publisher> {

    /**
     * Saves object.
     *
     * @param publisherCommand object to be saved
     * @return saved object
     */
    PublisherCommand savePublisherCommand(PublisherCommand publisherCommand);

    /**
     * Finds the object by provided id.
     *
     * @param id object id
     * @return found object
     */
    PublisherCommand findCommandById(Long id);

    /**
     * Gets books which belong to particular {@code Publisher}.
     *
     * @param id object id
     * @return list of books
     */
    List<Book> getPublishersBooks(Long id);
}
