package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.model.Book;

import java.util.Set;

public interface IndexService {

    Set<Book> searchByBookAndAuthor(Book book, Author author);
}
