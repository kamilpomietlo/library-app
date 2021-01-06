package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.Genre;
import com.kamilpomietlo.libraryapp.repositories.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenreServiceImplTest {

    private GenreService genreService;

    @Mock
    GenreRepository genreRepository;

    @BeforeEach
    void setUp() {
        genreService = new GenreServiceImpl(genreRepository);
    }

    // parent tests

    @Test
    void findAllGenres() {
        // given
        List<Genre> genres = new ArrayList<>();

        Genre genre1 = new Genre();
        genre1.setId(1L);

        Genre genre2 = new Genre();
        genre2.setId(2L);

        genres.add(genre1);
        genres.add(genre2);

        when(genreRepository.findAll()).thenReturn(genres);

        // when
        Set<Genre> genreSet = genreService.findAll();

        // then
        assertNotNull(genreSet);
        assertEquals(2, genreSet.size());
        verify(genreRepository, times(1)).findAll();
    }
}
