package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.model.ConfirmationToken;
import com.kamilpomietlo.libraryapp.services.ConfirmationTokenService;
import com.kamilpomietlo.libraryapp.services.IndexService;
import com.kamilpomietlo.libraryapp.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class IndexControllerTest {

    @InjectMocks
    private IndexController indexController;

    private MockMvc mockMvc;

    @Mock
    IndexService indexService;

    @Mock
    UserService userService;

    @Mock
    ConfirmationTokenService confirmationTokenService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
    }

    @Test
    void searchForm() throws Exception {
        // then
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("book", new Book()))
                .andExpect(model().attribute("author", new Author()))
                .andExpect(view().name("index"));

        verifyNoInteractions(indexService);
    }

    @Test
    void searchSubmit() throws Exception {
        // given
        Set<Book> books = new HashSet<>();

        Book book1 = new Book();
        book1.setId(1L);

        Book book2 = new Book();
        book2.setId(2L);

        books.add(book1);
        books.add(book2);

        // when
        when(indexService.findBooks(any(), any(), any())).thenReturn(books);

        // then
        mockMvc.perform(post("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("books", books))
                .andExpect(view().name("book/list"));

        verify(indexService, times(1)).findBooks(any(), any(), any());
    }

    @Test
    void registerUserGet() throws Exception {
        // then
        mockMvc.perform(get("/register/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("register"));

        verifyNoInteractions(indexService);
    }

    @Test
    void confirmMail() throws Exception {
        // given
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setConfirmationToken("token");

        Optional<ConfirmationToken> confirmationTokenOptional = Optional.of(confirmationToken);

        // when
        when(confirmationTokenService.findConfirmationTokenByToken(anyString())).thenReturn(confirmationTokenOptional);

        // then
        mockMvc.perform(get("/register/confirm")
                .param("token", "token"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));

        verify(confirmationTokenService, times(1)).findConfirmationTokenByToken(anyString());
        verify(userService, times(1)).confirmUser(any());
    }

    @Test
    void login() throws Exception {
        // then
        mockMvc.perform(get("/login/"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
}
