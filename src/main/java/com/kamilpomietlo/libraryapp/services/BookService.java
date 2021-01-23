package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.model.Book;

import java.util.List;

public interface BookService extends BaseService<Book> {

    BookCommand saveBookCommand(BookCommand bookCommand);
    BookCommand findCommandById(Long id);
    void reserveBook(BookCommand bookCommand);
    List<Book> getReservedBooks();
    void acceptBorrowingBook(Long id);
    List<Book> getBorrowedBooks();
    void acceptReturningBook(Long id);
}
