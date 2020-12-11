package com.kamilpomietlo.libraryapp.converters;

import com.kamilpomietlo.libraryapp.commands.UserCommand;
import com.kamilpomietlo.libraryapp.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserCommand implements Converter<User, UserCommand> {

    private final BookToBookCommand bookToBookCommand;

    public UserToUserCommand(BookToBookCommand bookToBookCommand) {
        this.bookToBookCommand = bookToBookCommand;
    }

    @Override
    public UserCommand convert(User source) {
        if (source == null) {
            return null;
        }

        final UserCommand userCommand = new UserCommand();
        userCommand.setId(source.getId());
        userCommand.setIdNumber(source.getIdNumber());
        userCommand.setCountry(source.getCountry());
        userCommand.setState(source.getState());
        userCommand.setCity(source.getCity());
        userCommand.setStreet(source.getStreet());
        userCommand.setHomeNumber(source.getHomeNumber());

        if (source.getBooks() != null && source.getBooks().size() > 0) {
            source.getBooks()
                    .forEach(book -> userCommand.getBooks().add(bookToBookCommand.convert(book)));
        }

        return userCommand;
    }
}
