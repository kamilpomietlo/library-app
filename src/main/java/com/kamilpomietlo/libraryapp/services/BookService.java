package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.model.Book;

import java.util.Set;

public interface BookService extends BaseService<Book> {

    BookCommand saveBookCommand(BookCommand bookCommand);
    BookCommand findCommandById(Long id);
    void reserveBook(BookCommand bookCommand);
    void acceptBorrowingBook(BookCommand bookCommand);
    Set<Book> getReservedBooks();
}
