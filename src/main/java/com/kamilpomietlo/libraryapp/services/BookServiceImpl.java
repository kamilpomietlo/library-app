package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.converters.BookCommandToBook;
import com.kamilpomietlo.libraryapp.converters.BookToBookCommand;
import com.kamilpomietlo.libraryapp.exceptions.NotFoundException;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.model.BookStatus;
import com.kamilpomietlo.libraryapp.repositories.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookCommandToBook bookCommandToBook;
    private final BookToBookCommand bookToBookCommand;
    private final String EXCEPTION_STRING = "Expected object not found.";

    public BookServiceImpl(BookRepository bookRepository, BookCommandToBook bookCommandToBook,
                           BookToBookCommand bookToBookCommand) {
        this.bookRepository = bookRepository;
        this.bookCommandToBook = bookCommandToBook;
        this.bookToBookCommand = bookToBookCommand;
    }

    @Override
    public Set<Book> getBooks() {
        Set<Book> bookSet = new HashSet<>();
        bookRepository.findAll().iterator().forEachRemaining(bookSet::add);

        return bookSet;
    }

    @Override
    public Set<Book> findByTitle(String title) {
        return bookRepository.findByTitleIgnoreCaseContaining(title.trim());
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public BookCommand saveBookCommand(BookCommand bookCommand) {
        Book detachedBook = bookCommandToBook.convert(bookCommand);
        Book savedBook = bookRepository.save(detachedBook);

        return bookToBookCommand.convert(savedBook);
    }

    @Override
    public Book findById(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);

        if (bookOptional.isEmpty()) {
            throw new NotFoundException(EXCEPTION_STRING);
        }

        return bookOptional.get();
    }

    @Override
    @Transactional
    public BookCommand findCommandById(Long id) {
        return bookToBookCommand.convert(findById(id));
    }

    // todo add unit test after full implementation (user account)
    @Override
    @Transactional
    public void reserveBook(BookCommand bookCommand) {
        Book book = findById(bookCommand.getId());
        bookCommand.setAuthors(book.getAuthors());

        if (bookCommand.getBookStatus() == BookStatus.AVAILABLE) {
            bookCommand.setBookStatus(BookStatus.RESERVED);

            // temp user setting
            bookCommand.setUserId(1L);
        }

        saveBookCommand(bookCommand);
    }

    // todo add unit test after full implementation (user account)
    @Override
    @Transactional
    public void borrowBook(BookCommand bookCommand) {
        Book book = findById(bookCommand.getId());
        bookCommand.setAuthors(book.getAuthors());

        if (bookCommand.getBookStatus() == BookStatus.RESERVED) {
            bookCommand.setBookStatus(BookStatus.BORROWED);

            // temp user setting
            bookCommand.setUserId(1L);
        }

        saveBookCommand(bookCommand);
    }
}
