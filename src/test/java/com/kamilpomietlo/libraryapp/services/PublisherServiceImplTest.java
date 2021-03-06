package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.PublisherCommand;
import com.kamilpomietlo.libraryapp.converters.BookCommandToBook;
import com.kamilpomietlo.libraryapp.converters.BookToBookCommand;
import com.kamilpomietlo.libraryapp.converters.PublisherCommandToPublisher;
import com.kamilpomietlo.libraryapp.converters.PublisherToPublisherCommand;
import com.kamilpomietlo.libraryapp.exceptions.NotFoundException;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.model.Publisher;
import com.kamilpomietlo.libraryapp.repositories.PublisherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublisherServiceImplTest {

    private PublisherService publisherService;
    private PublisherCommandToPublisher publisherCommandToPublisher;
    private PublisherToPublisherCommand publisherToPublisherCommand;

    @Mock
    PublisherRepository publisherRepository;

    @BeforeEach
    void setUp() {
        publisherCommandToPublisher = new PublisherCommandToPublisher(new BookCommandToBook());
        publisherToPublisherCommand = new PublisherToPublisherCommand(new BookToBookCommand());
        publisherService = new PublisherServiceImpl(publisherRepository, publisherCommandToPublisher,
                publisherToPublisherCommand);
    }

    @Test
    void findAllPublishers() {
        // given
        List<Publisher> publishers = new ArrayList<>();

        Publisher publisher1 = new Publisher();
        publisher1.setId(1L);

        Publisher publisher2 = new Publisher();
        publisher2.setId(2L);

        publishers.add(publisher1);
        publishers.add(publisher2);

        when(publisherRepository.findAll()).thenReturn(publishers);

        // when
        List<Publisher> publisherSet = publisherService.findAll();

        // then
        assertNotNull(publisherSet);
        assertEquals(2, publisherSet.size());
        verify(publisherRepository, times(1)).findAll();
    }

    @Test
    void findPublisherById() {
        // given
        Publisher publisher = new Publisher();
        publisher.setId(1L);

        Optional<Publisher> publisherOptional = Optional.of(publisher);

        when(publisherRepository.findById(anyLong())).thenReturn(publisherOptional);

        // when
        Publisher foundPublisher = publisherService.findById(publisher.getId());

        // then
        assertEquals(1L, foundPublisher.getId());
        verify(publisherRepository, times(1)).findById(anyLong());
    }

    @Test
    void findPublisherByIdNotFound() {
        // given
        Long publisherId = 1L;

        // when / then
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> publisherService.findById(publisherId));
        assertTrue(exception.getMessage().contains("Object not found"));
    }

    @Test
    void savePublisherCommand() {
        // given
        PublisherCommand publisherCommand = new PublisherCommand();
        publisherCommand.setId(1L);

        when(publisherRepository.save(any())).thenReturn(publisherCommandToPublisher.convert(publisherCommand));

        // when
        PublisherCommand savedPublisher = publisherService.savePublisherCommand(publisherCommand);

        // then
        assertEquals(1L, savedPublisher.getId());
        verify(publisherRepository, times(1)).save(any());
    }

    @Test
    void findCommandById() {
        // given
        Publisher publisher = new Publisher();
        publisher.setId(1L);

        Optional<Publisher> publisherOptional = Optional.of(publisher);

        when(publisherRepository.findById(anyLong())).thenReturn(publisherOptional);

        // when
        PublisherCommand foundPublisherCommand = publisherService.findCommandById(publisher.getId());

        // then
        assertEquals(1L, foundPublisherCommand.getId());
        verify(publisherRepository, times(1)).findById(anyLong());
    }

    @Test
    void getPublishersBooks() {
        // given
        Publisher publisher = new Publisher();
        publisher.setId(1L);

        Book book1 = new Book();
        book1.setId(1L);

        Book book2 = new Book();
        book2.setId(2L);

        publisher.getBooks().add(book1);
        publisher.getBooks().add(book2);

        Optional<Publisher> publisherOptional = Optional.of(publisher);

        when(publisherRepository.findById(anyLong())).thenReturn(publisherOptional);

        // when
        List<Book> books = publisherService.getPublishersBooks(publisher.getId());

        // then
        assertNotNull(books);
        assertEquals(2, books.size());
        verify(publisherRepository, times(1)).findById(anyLong());
    }
}
