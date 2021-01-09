package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.UserCommand;
import com.kamilpomietlo.libraryapp.converters.BookCommandToBook;
import com.kamilpomietlo.libraryapp.converters.BookToBookCommand;
import com.kamilpomietlo.libraryapp.converters.UserCommandToUser;
import com.kamilpomietlo.libraryapp.converters.UserToUserCommand;
import com.kamilpomietlo.libraryapp.exceptions.NotFoundException;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.model.ConfirmationToken;
import com.kamilpomietlo.libraryapp.model.User;
import com.kamilpomietlo.libraryapp.model.UserRole;
import com.kamilpomietlo.libraryapp.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private UserService userService;
    private UserCommandToUser userCommandToUser;
    private UserToUserCommand userToUserCommand;

    @Mock
    ConfirmationTokenService confirmationTokenService;

    @Mock
    EmailSenderService emailSenderService;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userCommandToUser = new UserCommandToUser(new BookCommandToBook());
        userToUserCommand = new UserToUserCommand(new BookToBookCommand());
        userService = new UserServiceImpl(userRepository, userCommandToUser, userToUserCommand,
                confirmationTokenService, emailSenderService);
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

    @Test
    void findCommandById() {
        // given
        User user = new User();
        user.setId(1L);

        Optional<User> userOptional = Optional.of(user);

        when(userRepository.findById(anyLong())).thenReturn(userOptional);

        // when
        UserCommand foundUserCommand = userService.findCommandById(user.getId());

        // then
        assertEquals(1L, foundUserCommand.getId());
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteById() {
        // given
        User user = new User();
        user.setId(1L);
        user.setUserRole(UserRole.USER);

        Optional<User> userOptional = Optional.of(user);

        when(userRepository.findById(anyLong())).thenReturn(userOptional);

        // when
        userService.deleteById(user.getId());

        // then
        verify(userRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteByIdWithAdminRole() {
        // given
        User user = new User();
        user.setId(1L);
        user.setUserRole(UserRole.ADMIN);

        Optional<User> userOptional = Optional.of(user);

        when(userRepository.findById(anyLong())).thenReturn(userOptional);

        // when
        userService.deleteById(user.getId());

        // then
        verify(userRepository, times(0)).deleteById(anyLong());
    }

    @Test
    void deleteByIdWithBooks() {
        // given
        User user = new User();
        user.setId(1L);
        user.setUserRole(UserRole.USER);

        Book book = new Book();
        book.setId(1L);

        user.addBook(book);

        Optional<User> userOptional = Optional.of(user);

        when(userRepository.findById(anyLong())).thenReturn(userOptional);

        // when
        userService.deleteById(user.getId());

        // then
        verify(userRepository, times(0)).deleteById(anyLong());
    }

    @Test
    void registerUser() {
        // given
        User user = new User();
        user.setId(1L);
        user.setPassword("123");

        when(userRepository.save(any())).thenReturn(user);

        // when
        userService.registerUser(userToUserCommand.convert(user));

        // then
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void confirmUser() {
        // given
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setId(1L);

        User user = new User();
        user.setId(1L);

        confirmationToken.setUser(user);

        when(userRepository.save(any())).thenReturn(user);

        // when
        userService.confirmUser(confirmationToken);

        // then
        verify(userRepository, times(1)).save(any());
        verify(confirmationTokenService, times(1)).deleteById(anyLong());
    }

    @Test
    void findUserByEmail() {
        // given
        User user = new User();
        user.setId(1L);
        user.setEmail("xyz@example.com");

        Optional<User> userOptional = Optional.of(user);

        when(userRepository.findByEmail(anyString())).thenReturn(userOptional);

        // when
        User foundUser = userService.findUserByEmail(user.getEmail());

        // then
        assertEquals(user.getEmail(), foundUser.getEmail());
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void findUserByEmailNotExisting() {
        // given
        String email = "xyz@example.com";

        // when / then
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> userService.findUserByEmail(email));
        assertTrue(exception.getMessage().contains(email));
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void editRemainingFields() {
        // given
        User dbUser = new User();
        dbUser.setId(1L);
        dbUser.setEmail("xyz@example.com");

        User editedUser = new User();
        editedUser.setId(1L);

        Optional<User> userOptional = Optional.of(dbUser);

        when(userRepository.findById(anyLong())).thenReturn(userOptional);

        // when
        UserCommand userCommand = userService.editRemainingFields(userToUserCommand.convert(editedUser));

        // then
        assertEquals(dbUser.getEmail(), userCommand.getEmail());
        verify(userRepository, times(1)).findById(anyLong());
    }

    // parent tests

    @Test
    void findAllUsers() {
        // given
        List<User> users = new ArrayList<>();

        User user1 = new User();
        user1.setId(1L);

        User user2 = new User();
        user2.setId(2L);

        users.add(user1);
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);

        // when
        Set<User> userSet = userService.findAll();

        // then
        assertNotNull(userSet);
        assertEquals(2, userSet.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findUserById() {
        // given
        User user = new User();
        user.setId(1L);

        Optional<User> userOptional = Optional.of(user);

        when(userRepository.findById(anyLong())).thenReturn(userOptional);

        // when
        User foundUser = userService.findById(user.getId());

        // then
        assertEquals(1L, foundUser.getId());
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void findUserByIdNotFound() {
        // given
        Long userId = 1L;

        // when / then
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> userService.findById(userId));
        assertTrue(exception.getMessage().contains("Object not found"));
    }
}
