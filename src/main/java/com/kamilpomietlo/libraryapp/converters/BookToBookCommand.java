package com.kamilpomietlo.libraryapp.converters;

import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.model.Book;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class BookToBookCommand implements Converter<Book, BookCommand> {

    @Synchronized
    @Nullable
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

        if (source.getGenre() != null) {
            bookCommand.setGenreId(source.getGenre().getId());
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
