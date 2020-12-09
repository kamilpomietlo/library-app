package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.AuthorCommand;
import com.kamilpomietlo.libraryapp.converters.AuthorCommandToAuthor;
import com.kamilpomietlo.libraryapp.converters.AuthorToAuthorCommand;
import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.repositories.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthorServiceImpl extends BaseServiceImpl<Author, AuthorRepository> implements AuthorService {

    private final AuthorCommandToAuthor authorCommandToAuthor;
    private final AuthorToAuthorCommand authorToAuthorCommand;

    public AuthorServiceImpl(AuthorRepository repository, AuthorCommandToAuthor authorCommandToAuthor,
                             AuthorToAuthorCommand authorToAuthorCommand) {
        super(repository);
        this.authorCommandToAuthor = authorCommandToAuthor;
        this.authorToAuthorCommand = authorToAuthorCommand;
    }

    @Override
    public AuthorCommand saveAuthorCommand(AuthorCommand authorCommand) {
        Author detachedAuthor = authorCommandToAuthor.convert(authorCommand);
        Author savedAuthor = repository.save(detachedAuthor);

        return authorToAuthorCommand.convert(savedAuthor);
    }

    @Override
    public AuthorCommand findCommandById(Long id) {
        return authorToAuthorCommand.convert(findById(id));
    }
}
