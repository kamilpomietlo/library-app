package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.UserCommand;
import com.kamilpomietlo.libraryapp.converters.UserCommandToUser;
import com.kamilpomietlo.libraryapp.converters.UserToUserCommand;
import com.kamilpomietlo.libraryapp.model.User;
import com.kamilpomietlo.libraryapp.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, UserRepository> implements UserService {

    private final UserCommandToUser userCommandToUser;
    private final UserToUserCommand userToUserCommand;

    public UserServiceImpl(UserRepository repository, UserCommandToUser userCommandToUser,
                           UserToUserCommand userToUserCommand) {
        super(repository);
        this.userCommandToUser = userCommandToUser;
        this.userToUserCommand = userToUserCommand;
    }

    @Override
    public UserCommand saveUserCommand(UserCommand userCommand) {
        User detachedUser = userCommandToUser.convert(userCommand);
        User savedUser = repository.save(detachedUser);

        return userToUserCommand.convert(savedUser);
    }

    @Override
    public UserCommand findCommandById(Long id) {
        return userToUserCommand.convert(findById(id));
    }
}
