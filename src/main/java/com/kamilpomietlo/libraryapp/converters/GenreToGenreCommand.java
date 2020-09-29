package com.kamilpomietlo.libraryapp.converters;

import com.kamilpomietlo.libraryapp.commands.GenreCommand;
import com.kamilpomietlo.libraryapp.model.Genre;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class GenreToGenreCommand implements Converter<Genre, GenreCommand> {

    private final BookToBookCommand bookToBookCommand;

    public GenreToGenreCommand(BookToBookCommand bookToBookCommand) {
        this.bookToBookCommand = bookToBookCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public GenreCommand convert(Genre source) {
        if (source == null) {
            return null;
        }

        final GenreCommand genreCommand = new GenreCommand();
        genreCommand.setId(source.getId());
        genreCommand.setDescription(source.getDescription());

        if (source.getBooks() != null && source.getBooks().size() > 0) {
            source.getBooks()
                    .forEach(book -> genreCommand.getBooks().add(bookToBookCommand.convert(book)));
        }

        return genreCommand;
    }
}
