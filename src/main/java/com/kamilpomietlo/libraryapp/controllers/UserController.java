package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.commands.UserCommand;
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

    @GetMapping("user/find")
    public String userSearchForm(Model model) {
        model.addAttribute("users", new User());

        return "user/find";
    }

    @PostMapping("user/find")
    public String userSearchSubmit(@ModelAttribute User user, Model model) {
        model.addAttribute("users", userService.findByIdNumber(user.getIdNumber()));

        return "user/list";
    }

    @GetMapping("user/delete")
    public String deleteByIdForm(Model model) {
        model.addAttribute("users", new User());

        return "user/delete";
    }

    @PostMapping("user/delete")
    public String deleteByIdSubmit(@ModelAttribute User user) {
        userService.deleteById(user.getId());

        return "redirect:/user/list";
    }

    @GetMapping("user/add")
    public String addNewUserForm(Model model) {
        model.addAttribute("users", new UserCommand());

        return "user/add";
    }

    @PostMapping("user/add")
    public String addNewUserSubmit(@ModelAttribute UserCommand userCommand) {
        userService.saveUserCommand(userCommand);

        return "redirect:/user/list";
    }
}
