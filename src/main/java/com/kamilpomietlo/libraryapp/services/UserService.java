package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    Set<User> getUsers();
    List<User> findByIdNumber(String idNumber);
}
