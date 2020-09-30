package com.kamilpomietlo.libraryapp.converters;

import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookToBookCommandTest {

    private static final Long ID = 1L;
    private static final String TITLE = "Title";
    private static final Long GENRE_ID = 1L;
    private static final Long PUBLISHER_ID = 1L;
    private static final CoverType COVER_TYPE = CoverType.SOFT;
    private static final Integer YEAR_OF_RELEASE = 2000;
    private static final String ISBN = "123";
    private static final Long USER_ID = 1L;
    private static final BookStatus BOOK_STATUS = BookStatus.AVAILABLE;
    private static BookToBookCommand bookToBookCommand;

    @BeforeEach
    void setUp() {
        bookToBookCommand = new BookToBookCommand();
    }

    @Test
    void testNullObject() {
        assertNull(bookToBookCommand.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(bookToBookCommand.convert(new Book()));
    }

    @Test
    void convert() {
        // given
        Book book = new Book();
        book.setId(ID);
        book.setTitle(TITLE);
        book.setCoverType(COVER_TYPE);
        book.setYearOfRelease(YEAR_OF_RELEASE);
        book.setIsbn(ISBN);
        book.setBookStatus(BOOK_STATUS);

        Genre genre = new Genre();
        genre.setId(GENRE_ID);

        Publisher publisher = new Publisher();
        publisher.setId(PUBLISHER_ID);

        User user = new User();
        user.setId(USER_ID);

        book.setGenre(genre);
        book.setPublisher(publisher);
        book.setUser(user);

        // when
        BookCommand bookCommand = bookToBookCommand.convert(book);

        // then
        assertNotNull(bookCommand);
        assertEquals(ID, bookCommand.getId());
        assertEquals(TITLE, bookCommand.getTitle());
        assertEquals(GENRE_ID, bookCommand.getGenreId());
        assertEquals(PUBLISHER_ID, bookCommand.getPublisherId());
        assertEquals(COVER_TYPE, bookCommand.getCoverType());
        assertEquals(YEAR_OF_RELEASE, bookCommand.getYearOfRelease());
        assertEquals(ISBN, bookCommand.getIsbn());
        assertEquals(USER_ID, bookCommand.getUserId());
        assertEquals(BOOK_STATUS, bookCommand.getBookStatus());
    }
}