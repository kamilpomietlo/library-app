package com.kamilpomietlo.libraryapp.converters;

import com.kamilpomietlo.libraryapp.commands.AuthorCommand;
import com.kamilpomietlo.libraryapp.model.Author;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * {@inheritDoc}
 */
@Component
public class AuthorToAuthorCommand implements Converter<Author, AuthorCommand> {

    private final BookToBookCommand bookToBookCommand;

    public AuthorToAuthorCommand(BookToBookCommand bookToBookCommand) {
        this.bookToBookCommand = bookToBookCommand;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthorCommand convert(Author source) {
        if (source == null) {
            return null;
        }

        final AuthorCommand authorCommand = new AuthorCommand();
        authorCommand.setId(source.getId());
        authorCommand.setName(source.getName());

        if (source.getBooks() != null && source.getBooks().size() > 0) {
            source.getBooks()
                    .forEach(book -> authorCommand.getBooks().add(bookToBookCommand.convert(book)));
        }

        return authorCommand;
    }
}
