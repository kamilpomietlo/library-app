package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.model.Genre;
import com.kamilpomietlo.libraryapp.repositories.AuthorRepository;
import com.kamilpomietlo.libraryapp.repositories.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Iterator;
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
    public Set<Book> findBooks(Book book, Author author, Genre genre) {
        Set<Book> searchedBooks;

        // if only genre provided, initialize set with all books
        if (book.getTitle().isEmpty() && author.getName().isEmpty() && genre != null) {
            searchedBooks = new HashSet<>(bookRepository.findAll());
        } else {
            searchedBooks = new HashSet<>();
        }

        // no book title but author name provided
        if (book.getTitle().isEmpty()) {
            if (!author.getName().isEmpty()) {
                Set<Author> authors = authorRepository.findByNameIgnoreCaseContaining(author.getName());

                for (Author searchedAuthor : authors) {
                    searchedAuthor.getBooks().iterator().forEachRemaining(searchedBooks::add);
                }
            }

            // filtering books by genre if provided
            return filterByGenre(searchedBooks, genre);
        }

        // no author name but book title provided
        if (author.getName().isEmpty()) {
            searchedBooks = bookRepository.findByTitleIgnoreCaseContaining(book.getTitle());

            // filtering books by genre if provided
            return filterByGenre(searchedBooks, genre);
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

        // filtering books by genre if provided
        return filterByGenre(searchedBooks, genre);
    }

    private Set<Book> filterByGenre(Set<Book> books, Genre genre) {
        Iterator<Book> iterator = books.iterator();

        if (genre != null) {
            while (iterator.hasNext()) {
                Book object = iterator.next();

                if (object.getGenre() != genre) {
                    iterator.remove();
                }
            }
        }

        return books;
    }
}
