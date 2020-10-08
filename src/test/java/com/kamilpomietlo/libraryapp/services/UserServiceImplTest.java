package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.UserCommand;
import com.kamilpomietlo.libraryapp.converters.BookCommandToBook;
import com.kamilpomietlo.libraryapp.converters.BookToBookCommand;
import com.kamilpomietlo.libraryapp.converters.UserCommandToUser;
import com.kamilpomietlo.libraryapp.converters.UserToUserCommand;
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
    private UserToUserCommand userToUserCommand;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userCommandToUser = new UserCommandToUser(new BookCommandToBook());
        userToUserCommand = new UserToUserCommand(new BookToBookCommand());
        userService = new UserServiceImpl(userRepository, userCommandToUser, userToUserCommand);
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

    @Test
    void deleteById() {
        // given
        Long userIdToDelete = 1L;

        // when
        userService.deleteById(userIdToDelete);

        // then
        verify(userRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void saveUserCommand() {
        // given
        UserCommand userCommand = new UserCommand();
        userCommand.setId(1L);

        when(userRepository.save(any())).thenReturn(userCommandToUser.convert(userCommand));

        // when
        UserCommand savedUserCommand = userService.saveUserCommand(userCommand);

        // then
        assertEquals(1L, savedUserCommand.getId());
        verify(userRepository, times(1)).save(any());
    }
}