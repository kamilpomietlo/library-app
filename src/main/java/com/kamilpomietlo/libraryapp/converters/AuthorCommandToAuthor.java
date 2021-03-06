package com.kamilpomietlo.libraryapp.converters;

import com.kamilpomietlo.libraryapp.commands.AuthorCommand;
import com.kamilpomietlo.libraryapp.model.Author;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * {@inheritDoc}
 */
@Component
public class AuthorCommandToAuthor implements Converter<AuthorCommand, Author> {

    private final BookCommandToBook bookCommandToBook;

    public AuthorCommandToAuthor(BookCommandToBook bookCommandToBook) {
        this.bookCommandToBook = bookCommandToBook;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Author convert(AuthorCommand source) {
        if (source == null) {
            return null;
        }

        final Author author = new Author();
        author.setId(source.getId());
        author.setName(source.getName());

        if (source.getBooks() != null && source.getBooks().size() > 0) {
            source.getBooks()
                    .forEach(book -> author.getBooks().add(bookCommandToBook.convert(book)));
        }

        return author;
    }
}
