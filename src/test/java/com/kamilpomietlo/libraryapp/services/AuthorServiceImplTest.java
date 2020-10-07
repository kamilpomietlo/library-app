package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.converters.AuthorCommandToAuthor;
import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.repositories.AuthorRepository;
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
class AuthorServiceImplTest {

    private AuthorService authorService;
    private AuthorCommandToAuthor authorCommandToAuthor;

    @Mock
    AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        authorService = new AuthorServiceImpl(authorRepository, authorCommandToAuthor);
    }

    @Test
    void getAuthors() {
        // given
        Set<Author> authors = new HashSet<>();

        Author author1 = new Author();
        author1.setId(1L);
        author1.setFirstName("first1");
        author1.setLastName("last1");

        Author author2 = new Author();
        author2.setId(2L);
        author2.setFirstName("first2");
        author2.setLastName("last2");

        authors.add(author1);
        authors.add(author2);

        when(authorRepository.findAll()).thenReturn(authors);

        // when
        Set<Author> authorSet = authorService.getAuthors();

        // then
        assertNotNull(authorSet);
        assertEquals(2, authorSet.size());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void findByFirstNameAndLastName() {
        // given
        Author author = new Author();

        author.setId(1L);
        author.setFirstName("first");
        author.setLastName("last");

        Optional<Author> authorOptional = Optional.of(author);

        when(authorRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(authorOptional);

        // when
        Set<Author> authorsReturned = authorService.findByFirstNameAndLastName("first", "last");

        // then
        assertNotNull(authorsReturned);
        assertEquals(1, authorsReturned.size());
        verify(authorRepository, times(1)).findByFirstNameAndLastName(anyString(), anyString());
        verify(authorRepository, never()).findAll();
    }
}