package com.kamilpomietlo.libraryapp.converters;

import com.kamilpomietlo.libraryapp.commands.PublisherCommand;
import com.kamilpomietlo.libraryapp.model.Publisher;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PublisherToPublisherCommand implements Converter<Publisher, PublisherCommand> {

    private final BookToBookCommand bookToBookCommand;

    public PublisherToPublisherCommand(BookToBookCommand bookToBookCommand) {
        this.bookToBookCommand = bookToBookCommand;
    }

    @Override
    public PublisherCommand convert(Publisher source) {
        if (source == null) {
            return null;
        }

        final PublisherCommand publisherCommand = new PublisherCommand();
        publisherCommand.setId(source.getId());
        publisherCommand.setName(source.getName());

        if (source.getBooks() != null && source.getBooks().size() > 0) {
            source.getBooks()
                    .forEach(book -> publisherCommand.getBooks().add(bookToBookCommand.convert(book)));
        }

        return publisherCommand;
    }
}
