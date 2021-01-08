package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.commands.UserCommand;
import com.kamilpomietlo.libraryapp.model.User;
import com.kamilpomietlo.libraryapp.services.MyUserDetailsService;
import com.kamilpomietlo.libraryapp.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @Mock
    UserService userService;

    @Mock
    MyUserDetailsService myUserDetailsService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void getUsers() throws Exception {
        // given
        Set<User> users = new HashSet<>();

        User user1 = new User();
        user1.setId(1L);

        User user2 = new User();
        user2.setId(2L);

        users.add(user1);
        users.add(user2);

        // when
        when(userService.findAll()).thenReturn(users);

        // then
        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("users", hasSize(2)))
                .andExpect(view().name("user/list"));

        verify(userService, times(1)).findAll();
    }

    @Test
    void deleteById() throws Exception {
        // then
        mockMvc.perform(get("/user/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));

        verify(userService, times(1)).deleteById(anyLong());
    }

    @Test
    void getAccountInfo() throws Exception {
        // given
        UserCommand userCommand = new UserCommand();
        userCommand.setId(1L);

        // when
        when(myUserDetailsService.getLoggedAccountId()).thenReturn(userCommand.getId());
        when(userService.findCommandById(anyLong())).thenReturn(userCommand);

        // then
        mockMvc.perform(get("/user/account"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userCommand))
                .andExpect(view().name("user/account"));

        verify(myUserDetailsService, times(1)).getLoggedAccountId();
        verify(userService, times(1)).findCommandById(anyLong());
    }

    @Test
    void editUserForm() throws Exception {
        // given
        UserCommand userCommand = new UserCommand();
        userCommand.setId(1L);

        // when
        when(myUserDetailsService.getLoggedAccountId()).thenReturn(userCommand.getId());
        when(userService.findCommandById(anyLong())).thenReturn(userCommand);

        // then
        mockMvc.perform(get("/user/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userCommand))
                .andExpect(view().name("user/edit"));

        verify(userService, times(1)).findCommandById(anyLong());
    }

    @Test
    void editUserSubmitValid() throws Exception {
        //then
        mockMvc.perform(post("/user/edit")
                .param("firstName", "firstName")
                .param("lastName", "lastName")
                .param("idNumber", "74012285959")
                .param("country", "country")
                .param("state", "state")
                .param("city", "city")
                .param("street", "street")
                .param("homeNumber", "homeNumber"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/account"));

        verify(userService, times(1)).editRemainingFields(any());
        verify(userService, times(1)).saveUserCommand(any());
    }

    @Test
    void editUserSubmitNotValid() throws Exception {
        //then
        mockMvc.perform(post("/user/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/edit"));

        verifyNoInteractions(userService);
    }
}
