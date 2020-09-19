package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("users/list")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getUsers());

        return "users/list";
    }
}
