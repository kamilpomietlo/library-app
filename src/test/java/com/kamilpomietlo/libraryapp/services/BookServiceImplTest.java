package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.converters.BookCommandToBook;
import com.kamilpomietlo.libraryapp.converters.BookToBookCommand;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    private BookService bookService;
    private BookCommandToBook bookCommandToBook;
    private BookToBookCommand bookToBookCommand;
    private MyUserDetailsService myUserDetailsService;

    @Mock
    BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookCommandToBook = new BookCommandToBook();
        bookToBookCommand = new BookToBookCommand();
        bookService = new BookServiceImpl(bookRepository, bookCommandToBook, bookToBookCommand, myUserDetailsService);
    }

    @Test
    void getBooks() {
        // given
        List<Book> books = new ArrayList<>();

        Book book1 = new Book();
        book1.setId(1L);

        Book book2 = new Book();
        book2.setId(2L);

        books.add(book1);
        books.add(book2);

        when(bookRepository.findAll()).thenReturn(books);

        // when
        Set<Book> bookSet = bookService.findAll();

        // then
        assertNotNull(bookSet);
        assertEquals(2, bookSet.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void deleteById() {
        // given
        Long bookIdToDelete = 1L;

        // when
        bookService.deleteById(bookIdToDelete);

        // then
        verify(bookRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void saveBookCommand() {
        // given
        BookCommand bookCommand = new BookCommand();
        bookCommand.setId(1L);

        when(bookRepository.save(any())).thenReturn(bookCommandToBook.convert(bookCommand));

        // when
        BookCommand savedBookCommand = bookService.saveBookCommand(bookCommand);

        // then
        assertEquals(1L, savedBookCommand.getId());
        verify(bookRepository, times(1)).save(any());
    }

    @Test
    void findById() {
        // given
        Book book = new Book();
        book.setId(1L);

        Optional<Book> bookOptional = Optional.of(book);

        when(bookRepository.findById(anyLong())).thenReturn(bookOptional);

        // when
        Book foundBook = bookService.findById(book.getId());

        // then
        assertEquals(1L, foundBook.getId());
        verify(bookRepository, times(1)).findById(anyLong());
    }

    @Test
    void findCommandById() {
        // given
        Book book = new Book();
        book.setId(1L);

        Optional<Book> bookOptional = Optional.of(book);

        when(bookRepository.findById(anyLong())).thenReturn(bookOptional);

        // when
        BookCommand foundBookCommand = bookService.findCommandById(book.getId());

        // then
        assertEquals(1L, foundBookCommand.getId());
        verify(bookRepository, times(1)).findById(anyLong());
    }
}