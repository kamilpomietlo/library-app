package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.UserCommand;
import com.kamilpomietlo.libraryapp.commands.UserRegisterCommand;
import com.kamilpomietlo.libraryapp.model.ConfirmationToken;
import com.kamilpomietlo.libraryapp.model.User;

public interface UserService extends BaseService<User> {

    UserCommand saveUserCommand(UserCommand userCommand);
    UserCommand findCommandById(Long id);
    void registerUser(UserRegisterCommand userRegisterCommand);
    boolean isEmailUsed(String email);
    void sendConfirmationMail(String userMail, String confirmationToken);
    void confirmUser(ConfirmationToken confirmationToken);
    User findUserByEmail(String email);
}
