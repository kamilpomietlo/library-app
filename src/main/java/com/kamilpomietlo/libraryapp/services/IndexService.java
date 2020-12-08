package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.model.Book;

import java.util.Set;

public interface IndexService extends BaseService<Book> {

    Set<Book> searchByBookAndAuthor(Book book, Author author);
}
