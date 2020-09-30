package com.kamilpomietlo.libraryapp.converters;

import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.model.BookStatus;
import com.kamilpomietlo.libraryapp.model.CoverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookCommandToBookTest {

    private static final Long ID = 1L;
    private static final String TITLE = "Title";
    private static final Long GENRE_ID = 1L;
    private static final Long PUBLISHER_ID = 1L;
    private static final CoverType COVER_TYPE = CoverType.SOFT;
    private static final Integer YEAR_OF_RELEASE = 2000;
    private static final String ISBN = "123";
    private static final Long USER_ID = 1L;
    private static final BookStatus BOOK_STATUS = BookStatus.AVAILABLE;
    private static BookCommandToBook bookCommandToBook;

    @BeforeEach
    void setUp() {
        bookCommandToBook = new BookCommandToBook();
    }

    @Test
    void testNullObject() {
        assertNull(bookCommandToBook.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(bookCommandToBook.convert(new BookCommand()));
    }

    @Test
    void convert() {
        // given
        BookCommand bookCommand = new BookCommand();
        bookCommand.setId(ID);
        bookCommand.setTitle(TITLE);
        bookCommand.setGenreId(GENRE_ID);
        bookCommand.setPublisherId(PUBLISHER_ID);
        bookCommand.setCoverType(COVER_TYPE);
        bookCommand.setYearOfRelease(YEAR_OF_RELEASE);
        bookCommand.setIsbn(ISBN);
        bookCommand.setUserId(USER_ID);
        bookCommand.setBookStatus(BOOK_STATUS);

        // when
        Book book = bookCommandToBook.convert(bookCommand);

        // then
        assertNotNull(book);
        assertEquals(ID, book.getId());
        assertEquals(TITLE, book.getTitle());
        assertEquals(GENRE_ID, book.getGenre().getId());
        assertEquals(PUBLISHER_ID, book.getPublisher().getId());
        assertEquals(COVER_TYPE, book.getCoverType());
        assertEquals(YEAR_OF_RELEASE, book.getYearOfRelease());
        assertEquals(ISBN, book.getIsbn());
        assertEquals(USER_ID, book.getUser().getId());
        assertEquals(BOOK_STATUS, book.getBookStatus());
    }
}