package com.kamilpomietlo.libraryapp.converters;

import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.model.Book;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * {@inheritDoc}
 */
@Component
public class BookToBookCommand implements Converter<Book, BookCommand> {

    /**
     * {@inheritDoc}
     */
    @Override
    public BookCommand convert(Book source) {
        if (source == null) {
            return null;
        }

        final BookCommand bookCommand = new BookCommand();
        bookCommand.setId(source.getId());
        bookCommand.setTitle(source.getTitle());
        bookCommand.setCoverType(source.getCoverType());
        bookCommand.setYearOfRelease(source.getYearOfRelease());
        bookCommand.setIsbn(source.getIsbn());
        bookCommand.setBookStatus(source.getBookStatus());
        bookCommand.setDateOfReserveOrBorrow(source.getDateOfReserveOrBorrow());
        bookCommand.setDeadlineDate(source.getDeadlineDate());

        if (source.getAuthors() != null) {
            bookCommand.setAuthors(source.getAuthors());
        }

        if (source.getGenre() != null) {
            bookCommand.setGenre(source.getGenre());
        }

        if (source.getPublisher() != null) {
            bookCommand.setPublisherId(source.getPublisher().getId());
        }

        if (source.getUser() != null) {
            bookCommand.setUserId(source.getUser().getId());
        }

        return bookCommand;
    }
}
