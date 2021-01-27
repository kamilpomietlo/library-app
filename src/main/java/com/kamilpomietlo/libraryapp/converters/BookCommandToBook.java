package com.kamilpomietlo.libraryapp.converters;

import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.model.Publisher;
import com.kamilpomietlo.libraryapp.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * {@inheritDoc}
 */
@Component
public class BookCommandToBook implements Converter<BookCommand, Book> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Book convert(BookCommand source) {
        if (source == null) {
            return null;
        }

        final Book book = new Book();
        book.setId(source.getId());
        book.setTitle(source.getTitle());
        book.setCoverType(source.getCoverType());
        book.setYearOfRelease(source.getYearOfRelease());
        book.setIsbn(source.getIsbn());
        book.setBookStatus(source.getBookStatus());
        book.setDateOfReserveOrBorrow(source.getDateOfReserveOrBorrow());
        book.setDeadlineDate(source.getDeadlineDate());

        if (source.getAuthors() != null) {
            book.setAuthors(source.getAuthors());
        }

        if (source.getGenre() != null) {
            book.setGenre(source.getGenre());
        }

        if (source.getPublisherId() != null) {
            Publisher publisher = new Publisher();
            publisher.setId(source.getPublisherId());
            book.setPublisher(publisher);
            publisher.getBooks().add(book);
        }

        if (source.getUserId() != null) {
            User user = new User();
            user.setId(source.getUserId());
            book.setUser(user);
            user.getBooks().add(book);
        }

        return book;
    }
}
