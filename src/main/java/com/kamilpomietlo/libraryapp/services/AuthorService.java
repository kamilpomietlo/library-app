package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.AuthorCommand;
import com.kamilpomietlo.libraryapp.model.Author;

import java.util.List;
import java.util.Set;

public interface AuthorService {

    Set<Author> getAuthors();
    List<Author> findByFirstNameAndLastName(String firstName, String lastName);
    void saveAuthorCommand(AuthorCommand authorCommand);
}
