package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.Author;

import java.util.Set;

public interface AuthorService {

    Set<Author> getAuthors();

//    Author findById(Long id);
}
