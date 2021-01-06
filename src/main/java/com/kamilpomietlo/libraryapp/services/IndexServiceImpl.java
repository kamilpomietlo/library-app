package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.repositories.AuthorRepository;
import com.kamilpomietlo.libraryapp.repositories.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class IndexServiceImpl implements IndexService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public IndexServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Set<Book> searchByBookAndAuthor(Book book, Author author) {
        Set<Book> searchedBooks = new HashSet<>();

        // no title provided
        if (book.getTitle().isEmpty()) {
            if (!author.getName().isEmpty()) {
                Set<Author> authors = authorRepository.findByNameIgnoreCaseContaining(author.getName());

                for (Author searchedAuthor : authors) {
                    searchedAuthor.getBooks().iterator().forEachRemaining(searchedBooks::add);
                }
            }

            return searchedBooks;
        }

        // no name provided
        if (author.getName().isEmpty()) {
            searchedBooks = bookRepository.findByTitleIgnoreCaseContaining(book.getTitle());

            return searchedBooks;
        }

        // both title and name provided
        Set<Book> books = bookRepository.findByTitleIgnoreCaseContaining(book.getTitle());
        Set<Author> authors = authorRepository.findByNameIgnoreCaseContaining(author.getName());

        for (Book searchedBook : books) {
            for (Author searchedAuthor : authors) {
                if (searchedBook.getAuthors().contains(searchedAuthor)) {
                    searchedBooks.add(searchedBook);
                }
            }
        }

        return searchedBooks;
    }
}
