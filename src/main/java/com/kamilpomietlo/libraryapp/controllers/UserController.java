package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.model.User;
import com.kamilpomietlo.libraryapp.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("user/list")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getUsers());

        return "user/list";
    }

    @GetMapping("user/search")
    public String userSearchForm(Model model) {
        model.addAttribute("searchedUser", new User());

        return "user/search";
    }

    @PostMapping("user/search")
    public String userSearchSubmit(@ModelAttribute User user, Model model) {
        model.addAttribute("users", userService.findByIdNumber(user.getIdNumber()));

        return "user/list";
    }
}
