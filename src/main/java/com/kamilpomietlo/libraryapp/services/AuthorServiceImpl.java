package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Set<Author> getAuthors() {
        Set<Author> authorSet = new HashSet<>();
        authorRepository.findAll().iterator().forEachRemaining(authorSet::add);

        return authorSet;
    }

    //    @Override
//    public Author findById(Long id) {
//        Optional<Author> authorOptional = authorRepository.findById(id);
//
//        if (authorOptional.isEmpty()) {
//            throw new NotFoundException("Author not found.");
//        }
//        return authorOptional.get();
//    }
}
