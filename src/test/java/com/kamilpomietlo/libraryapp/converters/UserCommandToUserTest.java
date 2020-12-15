package com.kamilpomietlo.libraryapp.converters;

import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.commands.UserCommand;
import com.kamilpomietlo.libraryapp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserCommandToUserTest {

    private static final Long ID = 1L;
    private static final String ID_NUMBER = "123";
    private static final String COUNTRY = "poland";
    private static final String STATE = "slaskie";
    private static final String CITY = "katowice";
    private static final String STREET = "nowa";
    private static final String HOME_NUMBER = "7";
    private static final Long BOOK_ID_1 = 1L;
    private static final Long BOOK_ID_2 = 2L;
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
        userCommand.setIdNumber(ID_NUMBER);
        userCommand.setCountry(COUNTRY);
        userCommand.setState(STATE);
        userCommand.setCity(CITY);
        userCommand.setStreet(STREET);
        userCommand.setHomeNumber(HOME_NUMBER);

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
        assertEquals(ID_NUMBER, user.getIdNumber());
        assertEquals(COUNTRY, user.getCountry());
        assertEquals(STATE, user.getState());
        assertEquals(CITY, user.getCity());
        assertEquals(STREET, user.getStreet());
        assertEquals(HOME_NUMBER, user.getHomeNumber());
        assertEquals(2, user.getBooks().size());
    }
}