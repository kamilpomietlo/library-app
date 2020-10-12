package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.converters.BookCommandToBook;
import com.kamilpomietlo.libraryapp.converters.BookToBookCommand;
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
        Set<Book> bookSet = new HashSet<>();
        Optional<Book> bookOptional = bookRepository.findByTitle(title);
        if (bookOptional.isEmpty()) {
            throw new RuntimeException(EXCEPTION_STRING);
        } else {
            bookSet.add(bookOptional.get());
        }

        return bookSet;
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public BookCommand saveBookCommand(BookCommand bookCommand) {
        Book detachedBook = bookCommandToBook.convert(bookCommand);

        if (detachedBook != null) {
            detachedBook.setBookStatus(BookStatus.AVAILABLE);
        }

        Book savedBook = bookRepository.save(detachedBook);

        return bookToBookCommand.convert(savedBook);
    }

//    @Override
//    public Book findById(Long id) {
//        Optional<Book> bookOptional = bookRepository.findById(id);
//
//        if (bookOptional.isEmpty()) {
//            throw new RuntimeException(EXCEPTION_STRING);
//        }
//
//        return bookOptional.get();
//    }
//
//    @Override
//    @Transactional
//    public BookCommand findCommandById(Long id) {
//        return bookToBookCommand.convert(findById(id));
//    }
//
//    @Override
//    @Transactional
//    public void reserveBook(Long id) {
//        BookCommand bookCommand = findCommandById(id);
//
//        Book bookToReserve = bookCommandToBook.convert(bookCommand);
//        bookToReserve.setBookStatus(BookStatus.RESERVED);
//
//        // todo don't work
////        if (bookToReserve.getBookStatus() == BookStatus.AVAILABLE) {
////            bookToReserve.setBookStatus(BookStatus.RESERVED);
////        }
//
////        BookCommand reservedBook = bookToBookCommand.convert(bookToReserve);
////        reservedBook.setIsbn("zxc");
////        reservedBook.setBookStatus(BookStatus.RESERVED);
//
//        saveBookCommand(bookToBookCommand.convert(bookToReserve));
//    }
}
