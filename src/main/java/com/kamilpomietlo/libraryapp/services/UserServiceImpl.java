package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.UserCommand;
import com.kamilpomietlo.libraryapp.converters.UserCommandToUser;
import com.kamilpomietlo.libraryapp.converters.UserToUserCommand;
import com.kamilpomietlo.libraryapp.model.User;
import com.kamilpomietlo.libraryapp.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserCommandToUser userCommandToUser;
    private final UserToUserCommand userToUserCommand;
    private final String EXCEPTION_STRING = "Expected object not found.";

    public UserServiceImpl(UserRepository userRepository, UserCommandToUser userCommandToUser,
                           UserToUserCommand userToUserCommand) {
        this.userRepository = userRepository;
        this.userCommandToUser = userCommandToUser;
        this.userToUserCommand = userToUserCommand;
    }

    @Override
    public Set<User> getUsers() {
        Set<User> userSet = new HashSet<>();
        userRepository.findAll().iterator().forEachRemaining(userSet::add);

        return userSet;
    }

    @Override
    public User findByIdNumber(String idNumber) {
        Optional<User> userOptional = userRepository.findByIdNumber(idNumber.trim());
        if (userOptional.isEmpty()) {
            throw new RuntimeException(EXCEPTION_STRING);
        }

        return userOptional.get();
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UserCommand saveUserCommand(UserCommand userCommand) {
        User detachedUser = userCommandToUser.convert(userCommand);
        User savedUser = userRepository.save(detachedUser);

        return userToUserCommand.convert(savedUser);
    }

    @Override
    public User findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new RuntimeException(EXCEPTION_STRING);
        }

        return userOptional.get();
    }

    @Override
    @Transactional
    public UserCommand findCommandById(Long id) {
        return userToUserCommand.convert(findById(id));
    }
}
