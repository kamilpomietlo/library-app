package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.AuthorCommand;
import com.kamilpomietlo.libraryapp.model.Author;

public interface AuthorService extends BaseService<Author> {

    AuthorCommand saveAuthorCommand(AuthorCommand authorCommand);
    AuthorCommand findCommandById(Long id);
}
