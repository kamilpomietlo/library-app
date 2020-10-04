package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.model.Book;

import java.util.List;
import java.util.Set;

public interface BookService {

    Set<Book> getBooks();
    List<Book> findByTitle(String title);
    void deleteById(Long id);
    void saveBookCommand(BookCommand bookCommand);
//    void reserveBook(Long id);
}
