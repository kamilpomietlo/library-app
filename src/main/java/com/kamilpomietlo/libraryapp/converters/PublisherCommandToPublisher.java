package com.kamilpomietlo.libraryapp.converters;

import com.kamilpomietlo.libraryapp.commands.PublisherCommand;
import com.kamilpomietlo.libraryapp.model.Publisher;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * {@inheritDoc}
 */
@Component
public class PublisherCommandToPublisher implements Converter<PublisherCommand, Publisher> {

    private final BookCommandToBook bookCommandToBook;

    public PublisherCommandToPublisher(BookCommandToBook bookCommandToBook) {
        this.bookCommandToBook = bookCommandToBook;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Publisher convert(PublisherCommand source) {
        if (source == null) {
            return null;
        }

        final Publisher publisher = new Publisher();
        publisher.setId(source.getId());
        publisher.setName(source.getName());

        if (source.getBooks() != null && source.getBooks().size() > 0) {
            source.getBooks()
                    .forEach(book -> publisher.getBooks().add(bookCommandToBook.convert(book)));
        }

        return publisher;
    }
}
