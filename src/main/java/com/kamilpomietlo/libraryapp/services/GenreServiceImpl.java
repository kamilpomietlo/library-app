package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.Genre;
import com.kamilpomietlo.libraryapp.repositories.GenreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GenreServiceImpl extends BaseServiceImpl<Genre, GenreRepository> implements GenreService {

    public GenreServiceImpl(GenreRepository repository) {
        super(repository);
    }
}
