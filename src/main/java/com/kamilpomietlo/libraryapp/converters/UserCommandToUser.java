package com.kamilpomietlo.libraryapp.converters;

import com.kamilpomietlo.libraryapp.commands.UserCommand;
import com.kamilpomietlo.libraryapp.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * {@inheritDoc}
 */
@Component
public class UserCommandToUser implements Converter<UserCommand, User> {

    private final BookCommandToBook bookCommandToBook;

    public UserCommandToUser(BookCommandToBook bookCommandToBook) {
        this.bookCommandToBook = bookCommandToBook;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User convert(UserCommand source) {
        if (source == null) {
            return null;
        }

        final User user = new User();
        user.setId(source.getId());
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

        user.setFirstName(source.getFirstName());
        user.setLastName(source.getLastName());
        user.setEmail(source.getEmail());
        user.setPassword(source.getPassword());
        user.setUserRole(source.getUserRole());
        user.setLocked(source.getLocked());
        user.setEnabled(source.getEnabled());

        return user;
    }
}
