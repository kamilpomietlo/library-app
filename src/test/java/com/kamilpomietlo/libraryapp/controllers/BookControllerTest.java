package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.model.BookStatus;
import com.kamilpomietlo.libraryapp.services.AuthorService;
import com.kamilpomietlo.libraryapp.services.BookService;
import com.kamilpomietlo.libraryapp.services.GenreService;
import com.kamilpomietlo.libraryapp.services.PublisherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;

    @Mock
    BookService bookService;

    @Mock
    AuthorService authorService;

    @Mock
    PublisherService publisherService;

    @Mock
    GenreService genreService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void getBooks() throws Exception {
        // given
        Set<Book> books = new HashSet<>();

        Book book1 = new Book();
        book1.setId(1L);

        Book book2 = new Book();
        book2.setId(2L);

        books.add(book1);
        books.add(book2);

        // when
        when(bookService.findAll()).thenReturn(books);

        // then
        mockMvc.perform(get("/book/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("books", hasSize(2)))
                .andExpect(view().name("book/list"));

        verify(bookService, times(1)).findAll();
    }

    @Test
    void deleteById() throws Exception {
        // then
        mockMvc.perform(get("/book/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book/list"));

        verify(bookService, times(1)).deleteById(anyLong());
    }

    @Test
    void addBookForm() throws Exception {
        // then
        mockMvc.perform(get("/book/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(view().name("book/add"));

        verifyNoInteractions(bookService);
    }

    @Test
    void addBookSubmitValid() throws Exception {
        // given
        Set<Author> authors = new HashSet<>();

        Author author1 = new Author();
        author1.setId(1L);

        Author author2 = new Author();
        author2.setId(2L);

        authors.add(author1);
        authors.add(author2);

        // then
        mockMvc.perform(post("/book/add")
                .param("title", "title")
                .param("authors", authors.toString())
                .param("yearOfRelease", "2000")
                .param("isbn", "9780670813643"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book/list"));

        verify(bookService, times(1)).saveBookCommand(any());
    }

    @Test
    void addBookSubmitNotValid() throws Exception {
        // then
        mockMvc.perform(post("/book/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/add"));

        verifyNoInteractions(bookService);
    }

    @Test
    void editBookForm() throws Exception {
        // given
        BookCommand bookCommand = new BookCommand();
        bookCommand.setId(1L);

        // when
        when(bookService.findCommandById(anyLong())).thenReturn(bookCommand);

        // then
        mockMvc.perform(get("/book/1/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("book", bookCommand))
                .andExpect(view().name("book/edit"));

        verify(bookService, times(1)).findCommandById(anyLong());
    }

    @Test
    void editBookSubmitValid() throws Exception {
        // given
        Set<Author> authors = new HashSet<>();

        Author author1 = new Author();
        author1.setId(1L);

        Author author2 = new Author();
        author2.setId(2L);

        authors.add(author1);
        authors.add(author2);

        BookCommand bookCommand = new BookCommand();
        bookCommand.setId(1L);
        bookCommand.setUserId(1L);
        bookCommand.setBookStatus(BookStatus.AVAILABLE);
        bookCommand.setDateOfReserveOrBorrow(LocalDate.now());
        bookCommand.setDeadlineDate(LocalDate.now());

        // when
        when(bookService.findCommandById(anyLong())).thenReturn(bookCommand);

        // then
        mockMvc.perform(post("/book/1/edit")
                .param("title", "title")
                .param("authors", authors.toString())
                .param("yearOfRelease", "2001")
                .param("isbn", "9780670813643"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book/list"));

        verify(bookService, times(1)).findCommandById(any());
        verify(bookService, times(1)).saveBookCommand(any());
    }

    @Test
    void editBookSubmitNotValid() throws Exception {
        // then
        mockMvc.perform(post("/book/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/edit"));

        verifyNoInteractions(bookService);
    }

    @Test
    void reserveBook() throws Exception {
        // then
        mockMvc.perform(get("/book/1/reserve"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/account"));

        verify(bookService, times(1)).findCommandById(anyLong());
        verify(bookService, times(1)).reserveBook(any());
    }

    @Test
    void getReservations() throws Exception {
        // given
        Set<Book> books = new HashSet<>();

        Book book1 = new Book();
        book1.setId(1L);

        Book book2 = new Book();
        book2.setId(2L);

        books.add(book1);
        books.add(book2);

        // when
        when(bookService.getReservedBooks()).thenReturn(books);

        // then
        mockMvc.perform(get("/book/reservations"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("books", books))
                .andExpect(view().name("book/reservations"));

        verify(bookService, times(1)).getReservedBooks();
    }

    @Test
    void acceptReservation() throws Exception {
        // then
        mockMvc.perform(get("/book/1/accept-reservation"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book/reservations"));

        verify(bookService, times(1)).acceptBorrowingBook(anyLong());
    }

    @Test
    void getBorrowedBooks() throws Exception {
        // given
        Set<Book> books = new HashSet<>();

        Book book1 = new Book();
        book1.setId(1L);

        Book book2 = new Book();
        book2.setId(2L);

        books.add(book1);
        books.add(book2);

        // when
        when(bookService.getBorrowedBooks()).thenReturn(books);

        // then
        mockMvc.perform(get("/book/borrowed"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("books", books))
                .andExpect(view().name("book/borrowed"));

        verify(bookService, times(1)).getBorrowedBooks();
    }

    @Test
    void acceptReturning() throws Exception {
        // then
        mockMvc.perform(get("/book/1/accept-returning"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book/borrowed"));

        verify(bookService, times(1)).acceptReturningBook(anyLong());
    }
}
