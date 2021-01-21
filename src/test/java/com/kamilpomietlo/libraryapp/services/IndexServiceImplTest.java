package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.model.Genre;
import com.kamilpomietlo.libraryapp.repositories.AuthorRepository;
import com.kamilpomietlo.libraryapp.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IndexServiceImplTest {

    private IndexService indexService;

    @Mock
    BookRepository bookRepository;

    @Mock
    AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        indexService = new IndexServiceImpl(bookRepository, authorRepository);
    }

    @Test
    void findBooks() {
        // given
        Set<Author> authors = new HashSet<>();
        Set<Book> books = new HashSet<>();

        Author author = new Author();
        author.setId(1L);
        author.setName("name");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("title");
        book.addAuthor(author);
        book.setGenre(Genre.FANTASY);

        Genre genre = Genre.FANTASY;

        books.add(book);
        authors.add(author);

        when(bookRepository.findByTitleIgnoreCaseContaining(anyString())).thenReturn(books);
        when(authorRepository.findByNameIgnoreCaseContaining(anyString())).thenReturn(authors);

        // when
        Set<Book> bookSet = indexService.findBooks(book, author, genre);

        // then
        assertNotNull(bookSet);
        assertEquals(1, bookSet.size());
        verify(bookRepository, times(1)).findByTitleIgnoreCaseContaining(anyString());
        verify(authorRepository, times(1)).findByNameIgnoreCaseContaining(anyString());
    }

    @Test
    void searchByBookAndAuthorEmptyFields() {
        // given
        Author author = new Author();
        author.setId(1L);
        author.setName("");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("");

        Genre genre = Genre.FANTASY;

        // when
        Set<Book> bookSet = indexService.findBooks(book, author, genre);

        // then
        assertNotNull(bookSet);
        assertEquals(0, bookSet.size());
        verify(bookRepository, times(0)).findByTitleIgnoreCaseContaining(anyString());
        verify(authorRepository, times(0)).findByNameIgnoreCaseContaining(anyString());
    }

    @Test
    void searchByBookAndAuthorOnlyBook() {
        // given
        Set<Book> books = new HashSet<>();

        Author author = new Author();
        author.setId(1L);
        author.setName("");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("title");
        book.addAuthor(author);
        book.setGenre(Genre.FANTASY);

        Genre genre = Genre.FANTASY;

        books.add(book);

        when(bookRepository.findByTitleIgnoreCaseContaining(anyString())).thenReturn(books);

        // when
        Set<Book> bookSet = indexService.findBooks(book, author, genre);

        // then
        assertNotNull(bookSet);
        assertEquals(1, bookSet.size());
        verify(bookRepository, times(1)).findByTitleIgnoreCaseContaining(anyString());
        verify(authorRepository, times(0)).findByNameIgnoreCaseContaining(anyString());
    }

    @Test
    void searchByBookAndAuthorOnlyAuthor() {
        // given
        Set<Author> authors = new HashSet<>();

        Author author = new Author();
        author.setId(1L);
        author.setName("name");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("");
        book.addAuthor(author);

        Genre genre = null;

        authors.add(author);

        when(authorRepository.findByNameIgnoreCaseContaining(anyString())).thenReturn(authors);

        // when
        Set<Book> bookSet = indexService.findBooks(book, author, genre);

        // then
        assertNotNull(bookSet);
        assertEquals(1, bookSet.size());
        verify(bookRepository, times(0)).findByTitleIgnoreCaseContaining(anyString());
        verify(authorRepository, times(1)).findByNameIgnoreCaseContaining(anyString());
    }
}
