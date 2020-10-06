package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.converters.BookCommandToBook;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.model.BookStatus;
import com.kamilpomietlo.libraryapp.repositories.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookCommandToBook bookCommandToBook;
    private final String EXCEPTION_STRING = "Expected object not found.";

    public BookServiceImpl(BookRepository bookRepository, BookCommandToBook bookCommandToBook) {
        this.bookRepository = bookRepository;
        this.bookCommandToBook = bookCommandToBook;
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
    @Transactional
    public void saveBookCommand(BookCommand bookCommand) {
        Book detachedBook = bookCommandToBook.convert(bookCommand);

        if (detachedBook != null) {
            detachedBook.setBookStatus(BookStatus.AVAILABLE);
            bookRepository.save(detachedBook);
        }
    }

    //    @Override
//    public void reserveBook(Long id) {
//        Book bookToReserve = bookRepository.findById(id).get();
//
//        // todo don't work, command objects to do first
//        if (bookToReserve.getBookStatus().equals(BookStatus.AVAILABLE)) {
//            bookToReserve.setBookStatus(BookStatus.RESERVED);
//        } else {
//
//        }
//    }
}
