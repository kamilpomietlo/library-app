package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final String EXCEPTION_STRING = "Expected object not found.";


    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Set<Author> getAuthors() {
        Set<Author> authorSet = new HashSet<>();
        authorRepository.findAll().iterator().forEachRemaining(authorSet::add);

        return authorSet;
    }

    @Override
    public List<Author> findByFirstNameAndLastName(String firstName, String lastName) {
        List<Author> authorList = new ArrayList<>();
        Optional<Author> authorOptional = authorRepository.findByFirstNameAndLastName(firstName, lastName);
        if (authorOptional.isEmpty()) {
            throw new RuntimeException(EXCEPTION_STRING);
        } else {
            authorList.add(authorOptional.get());
        }

        return authorList;
    }
}
