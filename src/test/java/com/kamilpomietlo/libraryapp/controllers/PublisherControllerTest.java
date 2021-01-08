package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.commands.PublisherCommand;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.model.Publisher;
import com.kamilpomietlo.libraryapp.services.PublisherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PublisherControllerTest {

    @InjectMocks
    private PublisherController publisherController;

    private MockMvc mockMvc;

    @Mock
    PublisherService publisherService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(publisherController).build();
    }

    @Test
    void getPublishers() throws Exception {
        // given
        Set<Publisher> publishers = new HashSet<>();

        Publisher publisher1 = new Publisher();
        publisher1.setId(1L);

        Publisher publisher2 = new Publisher();
        publisher2.setId(2L);

        publishers.add(publisher1);
        publishers.add(publisher2);

        // when
        when(publisherService.findAll()).thenReturn(publishers);

        // then
        mockMvc.perform(get("/publisher/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("publishers", hasSize(2)))
                .andExpect(view().name("publisher/list"));

        verify(publisherService, times(1)).findAll();
    }

    @Test
    void addPublisherForm() throws Exception {
        // then
        mockMvc.perform(get("/publisher/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("publishers"))
                .andExpect(view().name("publisher/add"));

        verifyNoInteractions(publisherService);
    }

    @Test
    void addPublisherSubmitValid() throws Exception {
        // then
        mockMvc.perform(post("/publisher/add")
                .param("name", "name"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/publisher/list"));

        verify(publisherService, times(1)).savePublisherCommand(any());
    }

    @Test
    void addPublisherSubmitNotValid() throws Exception {
        // then
        mockMvc.perform(post("/publisher/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("publisher/add"));

        verifyNoInteractions(publisherService);
    }

    @Test
    void editPublisherForm() throws Exception {
        // given
        PublisherCommand publisherCommand = new PublisherCommand();
        publisherCommand.setId(1L);

        // when
        when(publisherService.findCommandById(anyLong())).thenReturn(publisherCommand);

        // then
        mockMvc.perform(get("/publisher/1/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("publishers", publisherCommand))
                .andExpect(view().name("publisher/edit"));

        verify(publisherService, times(1)).findCommandById(anyLong());
    }

    @Test
    void editPublisherSubmitValid() throws Exception {
        //then
        mockMvc.perform(post("/publisher/1/edit")
                .param("name", "name"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/publisher/list"));

        verify(publisherService, times(1)).savePublisherCommand(any());
    }

    @Test
    void editPublisherSubmitNotValid() throws Exception {
        //then
        mockMvc.perform(post("/publisher/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("publisher/edit"));

        verifyNoInteractions(publisherService);
    }

    @Test
    void showBooks() throws Exception {
        // given
        Set<Book> books = new HashSet<>();

        Book book1 = new Book();
        book1.setId(1L);

        Book book2 = new Book();
        book2.setId(2L);

        books.add(book1);
        books.add(book2);

        // when
        when(publisherService.getPublishersBooks(anyLong())).thenReturn(books);

        // then
        mockMvc.perform(get("/publisher/1/show-books"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("books", hasSize(2)))
                .andExpect(view().name("book/list"));

        verify(publisherService, times(1)).getPublishersBooks(anyLong());
    }
}
