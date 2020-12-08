package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.AuthorCommand;
import com.kamilpomietlo.libraryapp.model.Author;

import java.util.Set;

public interface AuthorService extends BaseService<Author> {

    Set<Author> findByName(String name);
    AuthorCommand saveAuthorCommand(AuthorCommand authorCommand);
    AuthorCommand findCommandById(Long id);
}
