package com.kamilpomietlo.libraryapp.converters;

import com.kamilpomietlo.libraryapp.commands.UserRegisterCommand;
import com.kamilpomietlo.libraryapp.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserRegisterCommandToUser implements Converter<UserRegisterCommand, User> {

    @Override
    public User convert(UserRegisterCommand source) {
        if (source == null) {
            return null;
        }

        final User user = new User();
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
