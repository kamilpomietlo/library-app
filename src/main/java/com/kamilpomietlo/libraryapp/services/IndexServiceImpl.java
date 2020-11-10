package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.model.Book;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class IndexServiceImpl implements IndexService {

    private final BookService bookService;
    private final AuthorService authorService;

    public IndexServiceImpl(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @Override
    public Set<Book> searchByBookAndAuthor(Book book, Author author) {
        Set<Book> searchedBooks = new HashSet<>();

        // no title provided
        if (book.getTitle().isEmpty()) {
            if (!author.getName().isEmpty()) {
                Set<Author> authors = authorService.findByName(author.getName());

                for (Author searchedAuthor : authors) {
                    searchedAuthor.getBooks().iterator().forEachRemaining(searchedBooks::add);
                }
            }

            return searchedBooks;
        }

        // bo author's name provided
        if (author.getName().isEmpty()) {
            searchedBooks = bookService.findByTitle(book.getTitle());

            return searchedBooks;
        }

        // both title and author's name provided
        Set<Book> books = bookService.findByTitle(book.getTitle());
        Set<Author> authors = authorService.findByName(author.getName());

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
