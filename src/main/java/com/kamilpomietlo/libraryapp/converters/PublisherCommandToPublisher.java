package com.kamilpomietlo.libraryapp.converters;

import com.kamilpomietlo.libraryapp.commands.PublisherCommand;
import com.kamilpomietlo.libraryapp.model.Publisher;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class PublisherCommandToPublisher implements Converter<PublisherCommand, Publisher> {

    private final BookCommandToBook bookCommandToBook;

    public PublisherCommandToPublisher(BookCommandToBook bookCommandToBook) {
        this.bookCommandToBook = bookCommandToBook;
    }

    @Synchronized
    @Nullable
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
