package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.model.BookStatus;
import com.kamilpomietlo.libraryapp.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final String EXCEPTION_STRING = "Expected object not found.";

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Set<Book> getBooks() {
        Set<Book> bookSet = new HashSet<>();
        bookRepository.findAll().iterator().forEachRemaining(bookSet::add);

        return bookSet;
    }

    @Override
    public List<Book> findByTitle(String title) {
        List<Book> bookList = new ArrayList<>();
        Optional<Book> bookOptional = bookRepository.findByTitle(title);
        if (bookOptional.isEmpty()) {
            throw new RuntimeException(EXCEPTION_STRING);
        } else {
            bookList.add(bookOptional.get());
        }

        return bookList;
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void reserveBook(Long id) {
        Book bookToReserve = bookRepository.findById(id).get();

        // todo don't work, command objects to do first
        if (bookToReserve.getBookStatus().equals(BookStatus.AVAILABLE)) {
            bookToReserve.setBookStatus(BookStatus.RESERVED);
        } else {

        }
    }
}
