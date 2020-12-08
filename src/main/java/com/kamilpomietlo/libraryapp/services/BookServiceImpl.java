package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.converters.BookCommandToBook;
import com.kamilpomietlo.libraryapp.converters.BookToBookCommand;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.model.BookStatus;
import com.kamilpomietlo.libraryapp.repositories.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class BookServiceImpl extends BaseServiceImpl<Book, BookRepository> implements BookService {

    private final BookCommandToBook bookCommandToBook;
    private final BookToBookCommand bookToBookCommand;

    public BookServiceImpl(BookRepository repository, BookCommandToBook bookCommandToBook,
                           BookToBookCommand bookToBookCommand) {
        super(repository);
        this.bookCommandToBook = bookCommandToBook;
        this.bookToBookCommand = bookToBookCommand;
    }

    @Override
    public Set<Book> findByTitle(String title) {
        return repository.findByTitleIgnoreCaseContaining(title.trim());
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

    // todo add unit test after full implementation (user account)
    @Override
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
