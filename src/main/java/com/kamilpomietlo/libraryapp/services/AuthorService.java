package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.AuthorCommand;
import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.model.Book;

import java.util.List;

public interface AuthorService extends BaseService<Author> {

    AuthorCommand saveAuthorCommand(AuthorCommand authorCommand);
    AuthorCommand findCommandById(Long id);
    List<Book> getAuthorsBooks(Long id);
}
