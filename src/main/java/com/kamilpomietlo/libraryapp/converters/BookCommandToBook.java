package com.kamilpomietlo.libraryapp.converters;

import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.model.*;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class BookCommandToBook implements Converter<BookCommand, Book> {

    @Synchronized
    @Nullable
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

        if (source.getAuthors() != null) {
            Set<Author> authors = new HashSet<>(source.getAuthors());
            book.setAuthors(authors);
        }

        if (source.getGenreId() != null) {
            Genre genre = new Genre();
            genre.setId(source.getGenreId());
            book.setGenre(genre);
            genre.getBooks().add(book);
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
