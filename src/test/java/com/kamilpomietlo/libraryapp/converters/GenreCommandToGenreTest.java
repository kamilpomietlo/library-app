package com.kamilpomietlo.libraryapp.converters;

import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.commands.GenreCommand;
import com.kamilpomietlo.libraryapp.model.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenreCommandToGenreTest {

    private static final Long ID = 1L;
    private static final String DESCRIPTION = "description";
    private static final Long BOOK_ID_1 = 1L;
    private static final Long BOOK_ID_2 = 2L;
    private static GenreCommandToGenre genreCommandToGenre;

    @BeforeEach
    void setUp() {
        genreCommandToGenre = new GenreCommandToGenre(new BookCommandToBook());
    }

    @Test
    void testNullObject() {
        assertNull(genreCommandToGenre.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(genreCommandToGenre.convert(new GenreCommand()));
    }

    @Test
    void convert() {
        // given
        GenreCommand genreCommand = new GenreCommand();
        genreCommand.setId(ID);
        genreCommand.setDescription(DESCRIPTION);

        BookCommand book1 = new BookCommand();
        book1.setId(BOOK_ID_1);

        BookCommand book2 = new BookCommand();
        book2.setId(BOOK_ID_2);

        genreCommand.getBooks().add(book1);
        genreCommand.getBooks().add(book2);

        // when
        Genre genre = genreCommandToGenre.convert(genreCommand);

        // then
        assertNotNull(genre);
        assertEquals(ID, genre.getId());
        assertEquals(DESCRIPTION, genre.getDescription());
        assertEquals(2, genre.getBooks().size());
    }
}