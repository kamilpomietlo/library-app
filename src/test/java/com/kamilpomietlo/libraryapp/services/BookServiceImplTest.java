package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.repositories.BookRepository;
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
class BookServiceImplTest {

    private BookService bookService;

    @Mock
    BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookService = new BookServiceImpl(bookRepository);
    }

    @Test
    void getBooks() {
        // given
        Set<Book> books = new HashSet<>();

        Book book1 = new Book();
        book1.setId(1L);

        Book book2 = new Book();
        book2.setId(2L);

        books.add(book1);
        books.add(book2);

        when(bookRepository.findAll()).thenReturn(books);

        // when
        Set<Book> bookSet = bookService.getBooks();

        // then
        assertNotNull(bookSet);
        assertEquals(2, bookSet.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void findByTitle() {
        // given
        Book book = new Book();

        book.setId(1L);
        book.setTitle("title");

        Optional<Book> bookOptional = Optional.of(book);

        when(bookRepository.findByTitle(anyString())).thenReturn(bookOptional);

        // when
        List<Book> booksReturned = bookService.findByTitle("title");

        // then
        assertNotNull(booksReturned);
        assertEquals(1, booksReturned.size());
        verify(bookRepository, times(1)).findByTitle(anyString());
        verify(bookRepository, never()).findAll();
    }

    @Test
    void reserveBook() {
        // todo after implementation
    }
}