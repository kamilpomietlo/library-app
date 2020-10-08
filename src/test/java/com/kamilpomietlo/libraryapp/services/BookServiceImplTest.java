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

import java.util.HashSet;
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

    @Mock
    BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookCommandToBook = new BookCommandToBook();
        bookToBookCommand = new BookToBookCommand();
        bookService = new BookServiceImpl(bookRepository, bookCommandToBook, bookToBookCommand);
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
        Set<Book> booksReturned = bookService.findByTitle("title");

        // then
        assertNotNull(booksReturned);
        assertEquals(1, booksReturned.size());
        verify(bookRepository, times(1)).findByTitle(anyString());
        verify(bookRepository, never()).findAll();
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
}