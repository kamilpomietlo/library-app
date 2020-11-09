package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.AuthorCommand;
import com.kamilpomietlo.libraryapp.model.Author;

import java.util.Set;

public interface AuthorService {

    Set<Author> getAuthors();
    Set<Author> findByName(String name);
    AuthorCommand saveAuthorCommand(AuthorCommand authorCommand);
    Author findById(Long id);
    AuthorCommand findCommandById(Long id);
}
