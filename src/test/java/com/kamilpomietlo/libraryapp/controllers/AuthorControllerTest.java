package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.commands.AuthorCommand;
import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.services.AuthorService;
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
class AuthorControllerTest {

    @InjectMocks
    private AuthorController authorController;

    private MockMvc mockMvc;

    @Mock
    AuthorService authorService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
    }

    @Test
    void getAuthors() throws Exception {
        // given
        Set<Author> authors = new HashSet<>();

        Author author1 = new Author();
        author1.setId(1L);

        Author author2 = new Author();
        author2.setId(2L);

        authors.add(author1);
        authors.add(author2);

        // when
        when(authorService.findAll()).thenReturn(authors);

        // then
        mockMvc.perform(get("/author/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("authors", hasSize(2)))
                .andExpect(view().name("author/list"));

        verify(authorService, times(1)).findAll();
    }

    @Test
    void addAuthorForm() throws Exception {
        // then
        mockMvc.perform(get("/author/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("authors"))
                .andExpect(view().name("author/add"));

        verifyNoInteractions(authorService);
    }

    @Test
    void addAuthorSubmitValid() throws Exception {
        // then
        mockMvc.perform(post("/author/add")
                .param("name", "name"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/author/list"));

        verify(authorService, times(1)).saveAuthorCommand(any());
    }

    @Test
    void addAuthorSubmitNotValid() throws Exception {
        // then
        mockMvc.perform(post("/author/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("author/add"));

        verifyNoInteractions(authorService);
    }

    @Test
    void editAuthorForm() throws Exception {
        // given
        AuthorCommand authorCommand = new AuthorCommand();
        authorCommand.setId(1L);

        // when
        when(authorService.findCommandById(anyLong())).thenReturn(authorCommand);

        // then
        mockMvc.perform(get("/author/1/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("authors", authorCommand))
                .andExpect(view().name("author/edit"));

        verify(authorService, times(1)).findCommandById(anyLong());
    }

    @Test
    void editAuthorSubmitValid() throws Exception {
        //then
        mockMvc.perform(post("/author/1/edit")
                .param("name", "name"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/author/list"));

        verify(authorService, times(1)).saveAuthorCommand(any());
    }

    @Test
    void editAuthorSubmitNotValid() throws Exception {
        // then
        mockMvc.perform(post("/author/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("author/edit"));

        verifyNoInteractions(authorService);
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
        when(authorService.getAuthorsBooks(anyLong())).thenReturn(books);

        // then
        mockMvc.perform(get("/author/1/show-books"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("books", hasSize(2)))
                .andExpect(view().name("book/list"));

        verify(authorService, times(1)).getAuthorsBooks(anyLong());
    }
}
