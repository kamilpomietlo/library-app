package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.User;
import com.kamilpomietlo.libraryapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final String EXCEPTION_STRING = "Expected object not found.";

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Set<User> getUsers() {
        Set<User> userSet = new HashSet<>();
        userRepository.findAll().iterator().forEachRemaining(userSet::add);

        return userSet;
    }

    @Override
    public User findByIdNumber(String idNumber) {
        Optional<User> userOptional = userRepository.findByIdNumber(idNumber);
        if (userOptional.isEmpty()) {
            throw new RuntimeException(EXCEPTION_STRING);
        }

        return userOptional.get();
    }
}
