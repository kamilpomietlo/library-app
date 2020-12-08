package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.model.Book;

import java.util.Set;

public interface BookService extends BaseService<Book> {

    Set<Book> findByTitle(String title);
    BookCommand saveBookCommand(BookCommand bookCommand);
    BookCommand findCommandById(Long id);
    void reserveBook(BookCommand bookCommand);
    void borrowBook(BookCommand bookCommand);
}
