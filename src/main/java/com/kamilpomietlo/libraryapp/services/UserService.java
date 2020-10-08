package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.UserCommand;
import com.kamilpomietlo.libraryapp.model.User;

import java.util.Set;

public interface UserService {

    Set<User> getUsers();
    User findByIdNumber(String idNumber);
    void deleteById(Long id);
    UserCommand saveUserCommand(UserCommand userCommand);
}
