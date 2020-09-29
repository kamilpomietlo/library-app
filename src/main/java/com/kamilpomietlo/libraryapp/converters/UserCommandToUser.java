package com.kamilpomietlo.libraryapp.converters;

import com.kamilpomietlo.libraryapp.commands.UserCommand;
import com.kamilpomietlo.libraryapp.model.User;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UserCommandToUser implements Converter<UserCommand, User> {

    private final BookCommandToBook bookCommandToBook;

    public UserCommandToUser(BookCommandToBook bookCommandToBook) {
        this.bookCommandToBook = bookCommandToBook;
    }

    @Synchronized
    @Nullable
    @Override
    public User convert(UserCommand source) {
        if (source == null) {
            return null;
        }

        final User user = new User();
        user.setId(source.getId());
        user.setFirstName(source.getFirstName());
        user.setLastName(source.getLastName());
        user.setIdNumber(source.getIdNumber());
        user.setCountry(source.getCountry());
        user.setState(source.getState());
        user.setCity(source.getCity());
        user.setStreet(source.getStreet());
        user.setHomeNumber(source.getHomeNumber());

        if (source.getBooks() != null && source.getBooks().size() > 0) {
            source.getBooks()
                    .forEach(book -> user.getBooks().add(bookCommandToBook.convert(book)));
        }

        return user;
    }
}
