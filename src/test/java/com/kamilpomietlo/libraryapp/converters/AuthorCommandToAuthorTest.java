package com.kamilpomietlo.libraryapp.converters;

import com.kamilpomietlo.libraryapp.commands.AuthorCommand;
import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.model.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorCommandToAuthorTest {

    private static final Long ID = 1L;
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final Long BOOK_ID_1 = 1L;
    private static final Long BOOK_ID_2 = 2L;
    private static AuthorCommandToAuthor authorCommandToAuthor;

    @BeforeEach
    void setUp() {
        authorCommandToAuthor = new AuthorCommandToAuthor(new BookCommandToBook());
    }

    @Test
    void testNullObject() {
        assertNull(authorCommandToAuthor.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(authorCommandToAuthor.convert(new AuthorCommand()));
    }

    @Test
    void convert() {
        // given
        AuthorCommand authorCommand = new AuthorCommand();
        authorCommand.setId(ID);
        authorCommand.setFirstName(FIRST_NAME);
        authorCommand.setLastName(LAST_NAME);

        BookCommand bookCommand1 = new BookCommand();
        bookCommand1.setId(BOOK_ID_1);

        BookCommand bookCommand2 = new BookCommand();
        bookCommand2.setId(BOOK_ID_2);

        authorCommand.getBooks().add(bookCommand1);
        authorCommand.getBooks().add(bookCommand2);

        // when
        Author author = authorCommandToAuthor.convert(authorCommand);

        // then
        assertNotNull(author);
        assertEquals(ID, author.getId());
        assertEquals(FIRST_NAME, author.getFirstName());
        assertEquals(LAST_NAME, author.getLastName());
        assertEquals(2, author.getBooks().size());
    }
}