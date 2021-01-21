package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.model.Genre;

import java.util.Set;

public interface IndexService {

    Set<Book> findBooks(Book book, Author author, Genre genre);
}
