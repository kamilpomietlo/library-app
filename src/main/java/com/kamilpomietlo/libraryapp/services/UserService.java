package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.UserCommand;
import com.kamilpomietlo.libraryapp.model.User;

public interface UserService extends BaseService<User> {

    UserCommand saveUserCommand(UserCommand userCommand);
    UserCommand findCommandById(Long id);
}
