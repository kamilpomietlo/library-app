package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.converters.BookCommandToBook;
import com.kamilpomietlo.libraryapp.converters.BookToBookCommand;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.model.BookStatus;
import com.kamilpomietlo.libraryapp.repositories.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class BookServiceImpl extends BaseServiceImpl<Book, BookRepository> implements BookService {

    private final BookCommandToBook bookCommandToBook;
    private final BookToBookCommand bookToBookCommand;
    private final MyUserDetailsService myUserDetailsService;

    public BookServiceImpl(BookRepository repository, BookCommandToBook bookCommandToBook,
                           BookToBookCommand bookToBookCommand, MyUserDetailsService myUserDetailsService) {
        super(repository);
        this.bookCommandToBook = bookCommandToBook;
        this.bookToBookCommand = bookToBookCommand;
        this.myUserDetailsService = myUserDetailsService;
    }

    @Override
    public BookCommand saveBookCommand(BookCommand bookCommand) {
        Book detachedBook = bookCommandToBook.convert(bookCommand);
        Book savedBook = repository.save(detachedBook);

        return bookToBookCommand.convert(savedBook);
    }

    @Override
    public BookCommand findCommandById(Long id) {
        return bookToBookCommand.convert(findById(id));
    }

    @Override
    public void deleteById(Long id) {
        Book bookToDelete = findById(id);

        if (bookToDelete.getBookStatus() == BookStatus.AVAILABLE) {
            repository.deleteById(id);
        }
    }

    @Override
    public void reserveBook(BookCommand bookCommand) {
        Book book = findById(bookCommand.getId());
        bookCommand.setAuthors(book.getAuthors());

        Long currentUserId = myUserDetailsService.getLoggedAccountId();

        if (bookCommand.getBookStatus() == BookStatus.AVAILABLE) {
            bookCommand.setBookStatus(BookStatus.RESERVED);
            bookCommand.setDateOfReserveOrBorrow(LocalDate.now());
            bookCommand.setDeadlineDate(LocalDate.now().plusDays(7L));

            bookCommand.setUserId(currentUserId);
        }

        saveBookCommand(bookCommand);
    }

    @Override
    public Set<Book> getReservedBooks() {
        Set<Book> reservedBooks = new HashSet<>();

        for (Book book : repository.findAll()) {
            if (book.getBookStatus() == BookStatus.RESERVED) {
                reservedBooks.add(book);
            }
        }

        return reservedBooks;
    }

    @Override
    public void acceptBorrowingBook(Long id) {
        BookCommand bookCommand = findCommandById(id);

        if (bookCommand.getBookStatus() == BookStatus.RESERVED) {
            bookCommand.setBookStatus(BookStatus.BORROWED);
            bookCommand.setDateOfReserveOrBorrow(LocalDate.now());
            bookCommand.setDeadlineDate(LocalDate.now().plusDays(30L));
        }

        saveBookCommand(bookCommand);
    }

    @Override
    public Set<Book> getBorrowedBooks() {
        Set<Book> borrowedBooks = new HashSet<>();

        for (Book book : repository.findAll()) {
            if (book.getBookStatus() == BookStatus.BORROWED) {
                borrowedBooks.add(book);
            }
        }

        return borrowedBooks;
    }

    @Override
    public void acceptReturningBook(Long id) {
        BookCommand bookCommand = findCommandById(id);

        if (bookCommand.getBookStatus() == BookStatus.BORROWED) {
            bookCommand.setBookStatus(BookStatus.AVAILABLE);
            bookCommand.setUserId(null);
            bookCommand.setDateOfReserveOrBorrow(null);
            bookCommand.setDeadlineDate(null);
        }

        saveBookCommand(bookCommand);
    }
}
