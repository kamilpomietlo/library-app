package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.converters.BookCommandToBook;
import com.kamilpomietlo.libraryapp.converters.BookToBookCommand;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.model.BookStatus;
import com.kamilpomietlo.libraryapp.repositories.BookRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * {@inheritDoc}
 */
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

    @PostConstruct
    public void onStartup() {
        checkReservationDeadlines();
    }

    @Scheduled(cron="0 0 0 * * ?")
    public void onSchedule() {
        checkReservationDeadlines();
    }

    /**
     * Checks whether reserved books' deadlines have been exceeded.
     * Sets book status to Available, sets user and dates to null if so.
     */
    @Override
    public void checkReservationDeadlines() {
        LocalDate todayDate = LocalDate.now();
        List<Book> books = repository.findAllByBookStatus(BookStatus.RESERVED);

        for (Book book : books) {
            if (book.getDeadlineDate().isBefore(todayDate)) {
                book.setBookStatus(BookStatus.AVAILABLE);
                book.setUser(null);
                book.setDateOfReserveOrBorrow(null);
                book.setDeadlineDate(null);

                repository.save(book);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookCommand saveBookCommand(BookCommand bookCommand) {
        Book detachedBook = bookCommandToBook.convert(bookCommand);
        Book savedBook = repository.save(detachedBook);

        return bookToBookCommand.convert(savedBook);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookCommand findCommandById(Long id) {
        return bookToBookCommand.convert(findById(id));
    }

    /**
     * Deletes the {@code Book} object only if it's status is Available.
     *
     * @param id object id
     */
    @Override
    public void deleteById(Long id) {
        Book bookToDelete = findById(id);

        if (bookToDelete.getBookStatus() == BookStatus.AVAILABLE) {
            repository.deleteById(id);
        }
    }

    /**
     * Changes the {@code Book} status to Reserved, sets dates and links it to the specific {@code User}.
     *
     * @param bookCommand book to be reserved
     */
    @Override
    public void reserveBook(BookCommand bookCommand) {
        Book book = findById(bookCommand.getId());
        bookCommand.setAuthors(book.getAuthors());

        Long loggedUserId = myUserDetailsService.getLoggedAccountId();

        if (bookCommand.getBookStatus() == BookStatus.AVAILABLE) {
            bookCommand.setBookStatus(BookStatus.RESERVED);
            bookCommand.setDateOfReserveOrBorrow(LocalDate.now());
            bookCommand.setDeadlineDate(LocalDate.now().plusDays(7L));
            bookCommand.setUserId(loggedUserId);

            saveBookCommand(bookCommand);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Book> getReservedBooks() {
        List<Book> reservedBooks = new ArrayList<>();

        for (Book book : repository.findAll()) {
            if (book.getBookStatus() == BookStatus.RESERVED) {
                reservedBooks.add(book);
            }
        }

        return reservedBooks;
    }

    /**
     * Changes the {@code Book} status to Borrowed and sets new dates.
     *
     * @param id object id
     */
    @Override
    public void acceptBorrowingBook(Long id) {
        BookCommand bookCommand = findCommandById(id);

        if (bookCommand.getBookStatus() == BookStatus.RESERVED) {
            bookCommand.setBookStatus(BookStatus.BORROWED);
            bookCommand.setDateOfReserveOrBorrow(LocalDate.now());
            bookCommand.setDeadlineDate(LocalDate.now().plusDays(30L));

            saveBookCommand(bookCommand);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Book> getBorrowedBooks() {
        List<Book> borrowedBooks = new ArrayList<>();

        for (Book book : repository.findAll()) {
            if (book.getBookStatus() == BookStatus.BORROWED) {
                borrowedBooks.add(book);
            }
        }

        return borrowedBooks;
    }

    /**
     * Changes the {@code Book} status to Available, sets dates and {@code User} id to {@code null}.
     *
     * @param id object id
     */
    @Override
    public void acceptReturningBook(Long id) {
        BookCommand bookCommand = findCommandById(id);

        if (bookCommand.getBookStatus() == BookStatus.BORROWED) {
            bookCommand.setBookStatus(BookStatus.AVAILABLE);
            bookCommand.setUserId(null);
            bookCommand.setDateOfReserveOrBorrow(null);
            bookCommand.setDeadlineDate(null);
            bookCommand.setNumberOfProlongs(0L);

            saveBookCommand(bookCommand);
        }
    }

    /**
     * Prolongs the deadline of borrowed book by 30 days if current number of prolongs doesn't exceed 2 (what means
     * that the book can't be borrowed by a user for more than 90 days).
     *
     * @param id object id
     */
    @Override
    public void prolongBook(Long id) {
        Book book = findById(id);
        Long numberOfProlongs = book.getNumberOfProlongs();

        if ((book.getBookStatus() == BookStatus.BORROWED) && (numberOfProlongs < 2)) {
            book.setDeadlineDate(book.getDeadlineDate().plusDays(30));
            book.setNumberOfProlongs(++numberOfProlongs);
        }

        repository.save(book);
    }
}
