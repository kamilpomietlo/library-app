package com.kamilpomietlo.libraryapp.converters;

import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.commands.PublisherCommand;
import com.kamilpomietlo.libraryapp.model.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PublisherCommandToPublisherTest {

    private static final Long ID = 1L;
    private static final String NAME = "name";
    private static final Long BOOK_ID_1 = 1L;
    private static final Long BOOK_ID_2 = 2L;
    private static PublisherCommandToPublisher publisherCommandToPublisher;

    @BeforeEach
    void setUp() {
        publisherCommandToPublisher = new PublisherCommandToPublisher(new BookCommandToBook());
    }

    @Test
    void testNullObject() {
        assertNull(publisherCommandToPublisher.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(publisherCommandToPublisher.convert(new PublisherCommand()));
    }

    @Test
    void convert() {
        // given
        PublisherCommand publisherCommand = new PublisherCommand();
        publisherCommand.setId(ID);
        publisherCommand.setName(NAME);

        BookCommand book1 = new BookCommand();
        book1.setId(BOOK_ID_1);

        BookCommand book2 = new BookCommand();
        book2.setId(BOOK_ID_2);

        publisherCommand.getBooks().add(book1);
        publisherCommand.getBooks().add(book2);

        // when
        Publisher publisher = publisherCommandToPublisher.convert(publisherCommand);

        // then
        assertNotNull(publisher);
        assertEquals(ID, publisher.getId());
        assertEquals(NAME, publisher.getName());
        assertEquals(2, publisher.getBooks().size());
    }
}
