package com.kamilpomietlo.libraryapp.converters;

import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookToBookCommandTest {

    private static final Long ID = 1L;
    private static final String TITLE = "Title";
    private static final Long AUTHOR_ID_1 = 1L;
    private static final Long AUTHOR_ID_2 = 2L;
    private static final Genre GENRE = Genre.FANTASY;
    private static final Long PUBLISHER_ID = 1L;
    private static final CoverType COVER_TYPE = CoverType.SOFT;
    private static final Integer YEAR_OF_RELEASE = 2000;
    private static final String ISBN = "123";
    private static final Long USER_ID = 1L;
    private static final BookStatus BOOK_STATUS = BookStatus.AVAILABLE;
    private static final LocalDate RESERVE_BORROW_DATE = LocalDate.now();
    private static final LocalDate DEADLINE_DATE = LocalDate.now();
    private static final Long NUMBER_OF_PROLONGS = 0L;
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
        book.setGenre(GENRE);
        book.setCoverType(COVER_TYPE);
        book.setYearOfRelease(YEAR_OF_RELEASE);
        book.setIsbn(ISBN);
        book.setBookStatus(BOOK_STATUS);
        book.setDateOfReserveOrBorrow(RESERVE_BORROW_DATE);
        book.setDeadlineDate(DEADLINE_DATE);
        book.setNumberOfProlongs(NUMBER_OF_PROLONGS);

        Author author1 = new Author();
        author1.setId(AUTHOR_ID_1);

        Author author2 = new Author();
        author2.setId(AUTHOR_ID_2);

        Publisher publisher = new Publisher();
        publisher.setId(PUBLISHER_ID);

        User user = new User();
        user.setId(USER_ID);

        book.getAuthors().add(author1);
        book.getAuthors().add(author2);
        book.setPublisher(publisher);
        book.setUser(user);

        // when
        BookCommand bookCommand = bookToBookCommand.convert(book);

        // then
        assertNotNull(bookCommand);
        assertEquals(ID, bookCommand.getId());
        assertEquals(TITLE, bookCommand.getTitle());
        assertEquals(2, book.getAuthors().size());
        assertEquals(GENRE, bookCommand.getGenre());
        assertEquals(PUBLISHER_ID, bookCommand.getPublisherId());
        assertEquals(COVER_TYPE, bookCommand.getCoverType());
        assertEquals(YEAR_OF_RELEASE, bookCommand.getYearOfRelease());
        assertEquals(ISBN, bookCommand.getIsbn());
        assertEquals(USER_ID, bookCommand.getUserId());
        assertEquals(BOOK_STATUS, bookCommand.getBookStatus());
        assertEquals(RESERVE_BORROW_DATE, book.getDateOfReserveOrBorrow());
        assertEquals(DEADLINE_DATE, book.getDeadlineDate());
        assertEquals(NUMBER_OF_PROLONGS, book.getNumberOfProlongs());
    }
}
