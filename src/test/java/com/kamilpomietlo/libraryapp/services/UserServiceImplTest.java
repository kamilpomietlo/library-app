package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.converters.UserCommandToUser;
import com.kamilpomietlo.libraryapp.model.User;
import com.kamilpomietlo.libraryapp.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private UserService userService;
    private UserCommandToUser userCommandToUser;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, userCommandToUser);
    }

    @Test
    void getUsers() {
        // given
        Set<User> users = new HashSet<>();

        User user1 = new User();
        user1.setId(1L);

        User user2 = new User();
        user2.setId(2L);

        users.add(user1);
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);

        // when
        Set<User> userSet = userService.getUsers();

        // then
        assertNotNull(userSet);
        assertEquals(2, userSet.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findByIdNumber() {
        // given
        User user = new User();

        user.setId(1L);
        user.setIdNumber("123");

        Optional<User> userOptional = Optional.of(user);

        when(userRepository.findByIdNumber(anyString())).thenReturn(userOptional);

        // when
        User userReturned = userService.findByIdNumber("123");

        // then
        assertNotNull(userReturned);
        assertEquals(1, userReturned.getId());
        verify(userRepository, times(1)).findByIdNumber(anyString());
        verify(userRepository, never()).findAll();
    }
}