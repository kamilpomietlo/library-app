package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.converters.PublisherCommandToPublisher;
import com.kamilpomietlo.libraryapp.model.Publisher;
import com.kamilpomietlo.libraryapp.repositories.PublisherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublisherServiceImplTest {

    private PublisherService publisherService;
    private PublisherCommandToPublisher publisherCommandToPublisher;

    @Mock
    PublisherRepository publisherRepository;

    @BeforeEach
    void setUp() {
        publisherService = new PublisherServiceImpl(publisherRepository, publisherCommandToPublisher);
    }

    @Test
    void getPublishers() {
        // given
        Set<Publisher> publishers = new HashSet<>();

        Publisher publisher1 = new Publisher();
        publisher1.setId(1L);

        Publisher publisher2 = new Publisher();
        publisher2.setId(2L);

        publishers.add(publisher1);
        publishers.add(publisher2);

        when(publisherRepository.findAll()).thenReturn(publishers);

        // when
        Set<Publisher> publisherSet = publisherService.getPublishers();

        // then
        assertNotNull(publisherSet);
        assertEquals(2, publisherSet.size());
        verify(publisherRepository, times(1)).findAll();
    }

    @Test
    void findByName() {
        // given
        Publisher publisher = new Publisher();

        publisher.setId(1L);
        publisher.setName("name");

        Optional<Publisher> publisherOptional = Optional.of(publisher);

        when(publisherRepository.findByName(anyString())).thenReturn(publisherOptional);

        // when
        List<Publisher> publishersReturned = publisherService.findByName("name");

        // then
        assertNotNull(publishersReturned);
        assertEquals(1, publishersReturned.size());
        verify(publisherRepository, times(1)).findByName(anyString());
        verify(publisherRepository, never()).findAll();
    }
}