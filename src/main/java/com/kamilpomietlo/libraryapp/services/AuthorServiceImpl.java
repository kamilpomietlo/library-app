package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.AuthorCommand;
import com.kamilpomietlo.libraryapp.converters.AuthorCommandToAuthor;
import com.kamilpomietlo.libraryapp.converters.AuthorToAuthorCommand;
import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.repositories.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorCommandToAuthor authorCommandToAuthor;
    private final AuthorToAuthorCommand authorToAuthorCommand;
    private final String EXCEPTION_STRING = "Expected object not found.";


    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorCommandToAuthor authorCommandToAuthor,
                             AuthorToAuthorCommand authorToAuthorCommand) {
        this.authorRepository = authorRepository;
        this.authorCommandToAuthor = authorCommandToAuthor;
        this.authorToAuthorCommand = authorToAuthorCommand;
    }

    @Override
    public Set<Author> getAuthors() {
        Set<Author> authorSet = new HashSet<>();
        authorRepository.findAll().iterator().forEachRemaining(authorSet::add);

        return authorSet;
    }

    @Override
    public Set<Author> findByFirstNameAndLastName(String firstName, String lastName) {
        Set<Author> authorSet = new HashSet<>();
        Optional<Author> authorOptional = authorRepository.findByFirstNameAndLastName(firstName, lastName);
        if (authorOptional.isEmpty()) {
            throw new RuntimeException(EXCEPTION_STRING);
        } else {
            authorSet.add(authorOptional.get());
        }

        return authorSet;
    }

    @Override
    @Transactional
    public AuthorCommand saveAuthorCommand(AuthorCommand authorCommand) {
        Author detachedAuthor = authorCommandToAuthor.convert(authorCommand);
        Author savedAuthor = authorRepository.save(detachedAuthor);

        return authorToAuthorCommand.convert(savedAuthor);
    }
}
