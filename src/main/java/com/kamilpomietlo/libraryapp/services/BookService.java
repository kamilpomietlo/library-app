package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.model.Book;

import java.util.List;

/**
 * Provides operations related to {@code Book} objects.
 */
public interface BookService extends BaseService<Book> {

    /**
     * Checks whether reserved books' deadlines have been exceeded.
     */
    void checkReservationDeadlines();

    /**
     * Saves object.
     *
     * @param bookCommand object to be saved
     * @return saved object
     */
    BookCommand saveBookCommand(BookCommand bookCommand);

    /**
     * Finds the object by provided id.
     *
     * @param id object id
     * @return found object
     */
    BookCommand findCommandById(Long id);

    /**
     * Changes the {@code Book} status to Reserved.
     *
     * @param bookCommand book to be reserved
     */
    void reserveBook(BookCommand bookCommand);

    /**
     * Gets list of reserved books.
     *
     * @return list of books
     */
    List<Book> getReservedBooks();

    /**
     * Changes {@code Book} status from Reserved to Borrowed.
     *
     * @param id object id
     */
    void acceptBorrowingBook(Long id);

    /**
     * Gets list of borrowed books.
     *
     * @return list of books
     */
    List<Book> getBorrowedBooks();

    /**
     * Changes {@code Book} status from Borrowed to Available.
     *
     * @param id object id
     */
    void acceptReturningBook(Long id);

    /**
     * Prolongs the deadline of borrowed book.
     *
     * @param id object id
     */
    void prolongBook(Long id);

    /**
     * Cancels book reservation.
     *
     * @param id object id
     */
    void cancelReservation(Long id);

    /**
     * Updates all fields which were not sent via form.
     *
     * @param bookCommand object to be edited
     * @return edited object
     */
    BookCommand editRemainingFields(BookCommand bookCommand);
}
