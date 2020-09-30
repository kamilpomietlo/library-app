package com.kamilpomietlo.libraryapp.converters;

import com.kamilpomietlo.libraryapp.commands.GenreCommand;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.model.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenreToGenreCommandTest {

    private static final Long ID = 1L;
    private static final String DESCRIPTION = "description";
    private static final Long BOOK_ID_1 = 1L;
    private static final Long BOOK_ID_2 = 2L;
    private static GenreToGenreCommand genreToGenreCommand;

    @BeforeEach
    void setUp() {
        genreToGenreCommand = new GenreToGenreCommand(new BookToBookCommand());
    }

    @Test
    void testNullObject() {
        assertNull(genreToGenreCommand.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(genreToGenreCommand.convert(new Genre()));
    }

    @Test
    void convert() {
        // given
        Genre genre = new Genre();
        genre.setId(ID);
        genre.setDescription(DESCRIPTION);

        Book book1 = new Book();
        book1.setId(BOOK_ID_1);

        Book book2 = new Book();
        book2.setId(BOOK_ID_2);

        genre.getBooks().add(book1);
        genre.getBooks().add(book2);

        // when
        GenreCommand genreCommand = genreToGenreCommand.convert(genre);

        // then
        assertNotNull(genreCommand);
        assertEquals(ID, genreCommand.getId());
        assertEquals(DESCRIPTION, genreCommand.getDescription());
        assertEquals(2, genreCommand.getBooks().size());
    }
}