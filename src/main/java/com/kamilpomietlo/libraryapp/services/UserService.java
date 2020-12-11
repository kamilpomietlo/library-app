package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.UserCommand;
import com.kamilpomietlo.libraryapp.model.ConfirmationToken;
import com.kamilpomietlo.libraryapp.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService extends BaseService<User> {

    UserCommand saveUserCommand(UserCommand userCommand);
    UserCommand findCommandById(Long id);
    UserDetails findUserByUsername(String email);
    void signUpUser(User user);
    void confirmUser(ConfirmationToken confirmationToken);
    void sendConfirmationMail(String userMail, String confirmationToken);
}
