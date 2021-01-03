package com.kamilpomietlo.libraryapp.converters;

import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.commands.UserCommand;
import com.kamilpomietlo.libraryapp.model.User;
import com.kamilpomietlo.libraryapp.model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserCommandToUserTest {

    private static final Long ID = 1L;
    private static final String FIRST_NAME = "First name";
    private static final String LAST_NAME = "Last name";
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
    private static UserCommandToUser userCommandToUser;

    @BeforeEach
    void setUp() {
        userCommandToUser = new UserCommandToUser(new BookCommandToBook());
    }

    @Test
    void testNullObject() {
        assertNull(userCommandToUser.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(userCommandToUser.convert(new UserCommand()));
    }

    @Test
    void convert() {
        // given
        UserCommand userCommand = new UserCommand();
        userCommand.setId(ID);
        userCommand.setFirstName(FIRST_NAME);
        userCommand.setLastName(LAST_NAME);
        userCommand.setIdNumber(ID_NUMBER);
        userCommand.setCountry(COUNTRY);
        userCommand.setState(STATE);
        userCommand.setCity(CITY);
        userCommand.setStreet(STREET);
        userCommand.setHomeNumber(HOME_NUMBER);
        userCommand.setEmail(EMAIL);
        userCommand.setPassword(PASSWORD);
        userCommand.setUserRole(USER_ROLE);
        userCommand.setLocked(LOCKED);
        userCommand.setEnabled(ENABLED);

        BookCommand book1 = new BookCommand();
        book1.setId(BOOK_ID_1);

        BookCommand book2 = new BookCommand();
        book2.setId(BOOK_ID_2);

        userCommand.getBooks().add(book1);
        userCommand.getBooks().add(book2);

        // when
        User user = userCommandToUser.convert(userCommand);

        // then
        assertNotNull(user);
        assertEquals(ID, user.getId());
        assertEquals(FIRST_NAME, user.getFirstName());
        assertEquals(LAST_NAME, user.getLastName());
        assertEquals(ID_NUMBER, user.getIdNumber());
        assertEquals(COUNTRY, user.getCountry());
        assertEquals(STATE, user.getState());
        assertEquals(CITY, user.getCity());
        assertEquals(STREET, user.getStreet());
        assertEquals(HOME_NUMBER, user.getHomeNumber());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(PASSWORD, user.getPassword());
        assertEquals(USER_ROLE, user.getUserRole());
        assertEquals(LOCKED, user.getLocked());
        assertEquals(ENABLED, user.getEnabled());
        assertEquals(2, user.getBooks().size());
    }
}
