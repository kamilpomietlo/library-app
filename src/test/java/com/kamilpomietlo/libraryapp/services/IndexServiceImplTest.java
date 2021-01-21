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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    void findBooksEmptyFields() {
        // given
        Author author = new Author();
        author.setId(1L);
        author.setName("");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("");

        Genre genre = null;

        // when
        Set<Book> bookSet = indexService.findBooks(book, author, genre);

        // then
        assertNotNull(bookSet);
        assertEquals(0, bookSet.size());
        verify(bookRepository, times(0)).findByTitleIgnoreCaseContaining(anyString());
        verify(authorRepository, times(0)).findByNameIgnoreCaseContaining(anyString());
    }

    @Test
    void findBooksOnlyBook() {
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
    void findBooksOnlyAuthor() {
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

    @Test
    void findBooksOnlyGenre() {
        // given
        Set<Author> authors = new HashSet<>();
        List<Book> books = new ArrayList<>();

        Author author = new Author();
        author.setId(1L);
        author.setName("");

        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("");
        book1.setGenre(Genre.FANTASY);

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("");
        book2.setGenre(Genre.HISTORY);

        Book book3 = new Book();
        book3.setId(3L);
        book3.setTitle("");
        book3.setGenre(Genre.FANTASY);

        Genre genre = Genre.FANTASY;

        authors.add(author);
        books.add(book1);
        books.add(book2);
        books.add(book3);

        when(bookRepository.findAll()).thenReturn(books);

        // when
        Set<Book> bookSet = indexService.findBooks(book1, author, genre);

        // then
        assertNotNull(bookSet);
        assertEquals(2, bookSet.size());
        verify(bookRepository, times(0)).findByTitleIgnoreCaseContaining(anyString());
        verify(authorRepository, times(0)).findByNameIgnoreCaseContaining(anyString());
    }
}
