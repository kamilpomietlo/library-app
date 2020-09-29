package com.kamilpomietlo.libraryapp.converters;

import com.kamilpomietlo.libraryapp.commands.GenreCommand;
import com.kamilpomietlo.libraryapp.model.Genre;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class GenreCommandToGenre implements Converter<GenreCommand, Genre> {

    private final BookCommandToBook bookCommandToBook;

    public GenreCommandToGenre(BookCommandToBook bookCommandToBook) {
        this.bookCommandToBook = bookCommandToBook;
    }

    @Synchronized
    @Nullable
    @Override
    public Genre convert(GenreCommand source) {
        if (source == null) {
            return null;
        }

        final Genre genre = new Genre();
        genre.setId(source.getId());
        genre.setDescription(source.getDescription());

        if (source.getBooks() != null && source.getBooks().size() > 0) {
            source.getBooks()
                    .forEach(book -> genre.getBooks().add(bookCommandToBook.convert(book)));
        }

        return genre;
    }
}
