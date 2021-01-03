package com.kamilpomietlo.libraryapp.converters;

import com.kamilpomietlo.libraryapp.commands.UserCommand;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.model.User;
import com.kamilpomietlo.libraryapp.model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserToUserCommandTest {

    private static final Long ID = 1L;
    private static final String ID_NUMBER = "123";
    private static final String COUNTRY = "poland";
    private static final String STATE = "slaskie";
    private static final String CITY = "katowice";
    private static final String STREET = "nowa";
    private static final String HOME_NUMBER = "7";
    private static final Long BOOK_ID_1 = 1L;
    private static final Long BOOK_ID_2 = 2L;
    private static final String EMAIL = "email@email.com";
    private static final String PASSWORD = "qwe";
    private static final UserRole USER_ROLE = UserRole.USER;
    private static final Boolean LOCKED = false;
    private static final Boolean ENABLED = false;
    private static UserToUserCommand userToUserCommand;

    @BeforeEach
    void setUp() {
        userToUserCommand = new UserToUserCommand(new BookToBookCommand());
    }

    @Test
    void testNullObject() {
        assertNull(userToUserCommand.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(userToUserCommand.convert(new User()));
    }

    @Test
    void convert() {
        // given
        User user = new User();
        user.setId(ID);
        user.setIdNumber(ID_NUMBER);
        user.setCountry(COUNTRY);
        user.setState(STATE);
        user.setCity(CITY);
        user.setStreet(STREET);
        user.setHomeNumber(HOME_NUMBER);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        user.setUserRole(USER_ROLE);
        user.setLocked(LOCKED);
        user.setEnabled(ENABLED);

        Book book1 = new Book();
        book1.setId(BOOK_ID_1);

        Book book2 = new Book();
        book2.setId(BOOK_ID_2);

        user.getBooks().add(book1);
        user.getBooks().add(book2);

        // when
        UserCommand userCommand = userToUserCommand.convert(user);

        // then
        assertNotNull(userCommand);
        assertEquals(ID, userCommand.getId());
        assertEquals(ID_NUMBER, userCommand.getIdNumber());
        assertEquals(COUNTRY, userCommand.getCountry());
        assertEquals(STATE, userCommand.getState());
        assertEquals(CITY, userCommand.getCity());
        assertEquals(STREET, userCommand.getStreet());
        assertEquals(HOME_NUMBER, userCommand.getHomeNumber());
        assertEquals(EMAIL, userCommand.getEmail());
        assertEquals(PASSWORD, userCommand.getPassword());
        assertEquals(USER_ROLE, userCommand.getUserRole());
        assertEquals(LOCKED, userCommand.getLocked());
        assertEquals(ENABLED, userCommand.getEnabled());
        assertEquals(2, userCommand.getBooks().size());
    }
}
