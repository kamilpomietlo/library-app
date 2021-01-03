package com.kamilpomietlo.libraryapp.converters;

import com.kamilpomietlo.libraryapp.commands.PublisherCommand;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.model.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PublisherToPublisherCommandTest {

    private static final Long ID = 1L;
    private static final String NAME = "name";
    private static final Long BOOK_ID_1 = 1L;
    private static final Long BOOK_ID_2 = 2L;
    private static PublisherToPublisherCommand publisherToPublisherCommand;

    @BeforeEach
    void setUp() {
        publisherToPublisherCommand = new PublisherToPublisherCommand(new BookToBookCommand());
    }

    @Test
    void testNullObject() {
        assertNull(publisherToPublisherCommand.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(publisherToPublisherCommand.convert(new Publisher()));
    }

    @Test
    void convert() {
        // given
        Publisher publisher = new Publisher();
        publisher.setId(ID);
        publisher.setName(NAME);

        Book book1 = new Book();
        book1.setId(BOOK_ID_1);

        Book book2 = new Book();
        book2.setId(BOOK_ID_2);

        publisher.getBooks().add(book1);
        publisher.getBooks().add(book2);

        // when
        PublisherCommand publisherCommand = publisherToPublisherCommand.convert(publisher);

        // then
        assertNotNull(publisherCommand);
        assertEquals(ID, publisherCommand.getId());
        assertEquals(NAME, publisherCommand.getName());
        assertEquals(2, publisherCommand.getBooks().size());
    }
}
