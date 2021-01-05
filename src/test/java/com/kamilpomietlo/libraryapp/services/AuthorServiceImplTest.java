package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.AuthorCommand;
import com.kamilpomietlo.libraryapp.converters.AuthorCommandToAuthor;
import com.kamilpomietlo.libraryapp.converters.AuthorToAuthorCommand;
import com.kamilpomietlo.libraryapp.converters.BookCommandToBook;
import com.kamilpomietlo.libraryapp.converters.BookToBookCommand;
import com.kamilpomietlo.libraryapp.exceptions.NotFoundException;
import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.repositories.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    private AuthorService authorService;
    private AuthorCommandToAuthor authorCommandToAuthor;
    private AuthorToAuthorCommand authorToAuthorCommand;

    @Mock
    AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        authorCommandToAuthor = new AuthorCommandToAuthor(new BookCommandToBook());
        authorToAuthorCommand = new AuthorToAuthorCommand(new BookToBookCommand());
        authorService = new AuthorServiceImpl(authorRepository, authorCommandToAuthor, authorToAuthorCommand);
    }

    @Test
    void saveAuthorCommand() {
        // given
        AuthorCommand authorCommand = new AuthorCommand();
        authorCommand.setId(1L);

        when(authorRepository.save(any())).thenReturn(authorCommandToAuthor.convert(authorCommand));

        // when
        AuthorCommand savedAuthorCommand = authorService.saveAuthorCommand(authorCommand);

        // then
        assertEquals(1L, savedAuthorCommand.getId());
        verify(authorRepository, times(1)).save(any());
    }

    @Test
    void findCommandById() {
        // given
        Author author = new Author();
        author.setId(1L);

        Optional<Author> authorOptional = Optional.of(author);

        when(authorRepository.findById(anyLong())).thenReturn(authorOptional);

        // when
        AuthorCommand foundAuthorCommand = authorService.findCommandById(author.getId());

        // then
        assertEquals(1L, foundAuthorCommand.getId());
        verify(authorRepository, times(1)).findById(anyLong());
    }

    @Test
    void getAuthorsBooks() {
        // given
        Author author = new Author();
        author.setId(1L);

        Book book1 = new Book();
        book1.setId(1L);

        Book book2 = new Book();
        book2.setId(2L);

        author.getBooks().add(book1);
        author.getBooks().add(book2);

        Optional<Author> authorOptional = Optional.of(author);

        when(authorRepository.findById(anyLong())).thenReturn(authorOptional);

        // when
        Set<Book> books = authorService.getAuthorsBooks(author.getId());

        // then
        assertNotNull(books);
        assertEquals(2, books.size());
        verify(authorRepository, times(1)).findById(anyLong());
    }

    // parent tests

    @Test
    void findAllAuthors() {
        // given
        List<Author> authors = new ArrayList<>();

        Author author1 = new Author();
        author1.setId(1L);
        author1.setName("name1");

        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("name2");

        authors.add(author1);
        authors.add(author2);

        when(authorRepository.findAll()).thenReturn(authors);

        // when
        Set<Author> authorSet = authorService.findAll();

        // then
        assertNotNull(authorSet);
        assertEquals(2, authorSet.size());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void findAuthorById() {
        // given
        Author author = new Author();
        author.setId(1L);

        Optional<Author> authorOptional = Optional.of(author);

        when(authorRepository.findById(anyLong())).thenReturn(authorOptional);

        // when
        Author foundAuthor = authorService.findById(author.getId());

        // then
        assertEquals(1L, foundAuthor.getId());
        verify(authorRepository, times(1)).findById(anyLong());
    }

    @Test
    void findAuthorByIdNotFound() {
        // given
        Long authorId = 1L;

        // when / then
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> authorService.findById(authorId));
        assertTrue(exception.getMessage().contains("Object not found"));
    }
}
