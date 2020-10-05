package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.Genre;
import com.kamilpomietlo.libraryapp.repositories.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Set<Genre> getGenres() {
        Set<Genre> genreSet = new HashSet<>();
        genreRepository.findAll().iterator().forEachRemaining(genreSet::add);

        return genreSet;
    }
}
