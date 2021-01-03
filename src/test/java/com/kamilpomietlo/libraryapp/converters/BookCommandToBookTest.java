package com.kamilpomietlo.libraryapp.converters;

import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.model.BookStatus;
import com.kamilpomietlo.libraryapp.model.CoverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookCommandToBookTest {

    private static final Long ID = 1L;
    private static final String TITLE = "Title";
    private static final Long AUTHOR_ID_1 = 1L;
    private static final Long AUTHOR_ID_2 = 2L;
    private static final Long GENRE_ID = 1L;
    private static final Long PUBLISHER_ID = 1L;
    private static final CoverType COVER_TYPE = CoverType.SOFT;
    private static final Integer YEAR_OF_RELEASE = 2000;
    private static final String ISBN = "123";
    private static final Long USER_ID = 1L;
    private static final BookStatus BOOK_STATUS = BookStatus.AVAILABLE;
    private static final LocalDate RESERVE_BORROW_DATE = LocalDate.now();
    private static final LocalDate DEADLINE_DATE = LocalDate.now();
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
        bookCommand.setDateOfReserveOrBorrow(RESERVE_BORROW_DATE);
        bookCommand.setDeadlineDate(DEADLINE_DATE);

        Author author1 = new Author();
        author1.setId(AUTHOR_ID_1);

        Author author2 = new Author();
        author2.setId(AUTHOR_ID_2);

        bookCommand.getAuthors().add(author1);
        bookCommand.getAuthors().add(author2);

        // when
        Book book = bookCommandToBook.convert(bookCommand);

        // then
        assertNotNull(book);
        assertEquals(ID, book.getId());
        assertEquals(TITLE, book.getTitle());
        assertEquals(2, book.getAuthors().size());
        assertEquals(GENRE_ID, book.getGenre().getId());
        assertEquals(PUBLISHER_ID, book.getPublisher().getId());
        assertEquals(COVER_TYPE, book.getCoverType());
        assertEquals(YEAR_OF_RELEASE, book.getYearOfRelease());
        assertEquals(ISBN, book.getIsbn());
        assertEquals(USER_ID, book.getUser().getId());
        assertEquals(BOOK_STATUS, book.getBookStatus());
        assertEquals(RESERVE_BORROW_DATE, book.getDateOfReserveOrBorrow());
        assertEquals(DEADLINE_DATE, book.getDeadlineDate());
    }
}
