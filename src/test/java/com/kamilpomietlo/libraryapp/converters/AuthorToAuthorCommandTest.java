package com.kamilpomietlo.libraryapp.converters;

import com.kamilpomietlo.libraryapp.commands.AuthorCommand;
import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorToAuthorCommandTest {

    private static final Long ID = 1L;
    private static final String NAME = "name";
    private static final Long BOOK_ID_1 = 1L;
    private static final Long BOOK_ID_2 = 2L;
    private static AuthorToAuthorCommand authorToAuthorCommand;

    @BeforeEach
    void setUp() {
        authorToAuthorCommand = new AuthorToAuthorCommand(new BookToBookCommand());
    }

    @Test
    void testNullObject() {
        assertNull(authorToAuthorCommand.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(authorToAuthorCommand.convert(new Author()));
    }

    @Test
    void convert() {
        // given
        Author author = new Author();
        author.setId(ID);
        author.setName(NAME);

        Book book1 = new Book();
        book1.setId(BOOK_ID_1);

        Book book2 = new Book();
        book2.setId(BOOK_ID_2);

        author.getBooks().add(book1);
        author.getBooks().add(book2);

        // when
        AuthorCommand authorCommand = authorToAuthorCommand.convert(author);

        // then
        assertNotNull(authorCommand);
        assertEquals(ID, authorCommand.getId());
        assertEquals(NAME, authorCommand.getName());
        assertEquals(2, authorCommand.getBooks().size());
    }
}
