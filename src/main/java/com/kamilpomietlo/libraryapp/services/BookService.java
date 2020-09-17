package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.Book;

import java.util.Set;

public interface BookService {

    Set<Book> getBooks();
}
