package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.commands.UserCommand;
import com.kamilpomietlo.libraryapp.model.User;
import com.kamilpomietlo.libraryapp.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequestMapping("user")
@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.findAll());

        return "user/list";
    }

    @GetMapping("/delete")
    public String deleteByIdForm(Model model) {
        model.addAttribute("users", new User());

        return "user/delete";
    }

    @PostMapping("/delete")
    public String deleteByIdSubmit(@ModelAttribute User user) {
        userService.deleteById(user.getId());

        return "redirect:/user/list";
    }

    @GetMapping("/add")
    public String addUserForm(Model model) {
        model.addAttribute("users", new UserCommand());

        return "user/add";
    }

    @PostMapping("/add")
    public String addUserSubmit(@Valid @ModelAttribute("users") UserCommand userCommand, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            return "user/add";
        }

        userService.saveUserCommand(userCommand);

        return "redirect:/index";
    }

    @GetMapping("/{id}/edit")
    public String editUserForm(@PathVariable String id, Model model) {
        model.addAttribute("users", userService.findCommandById(Long.valueOf(id)));

        return "user/edit";
    }

    @PostMapping("/{id}/edit")
    public String editUserSubmit(@Valid @ModelAttribute("users") UserCommand userCommand, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            return "user/edit";
        }

        UserCommand oldUserCommand = userService.findCommandById(userCommand.getId());
        userCommand.setBooks(oldUserCommand.getBooks());

        userService.saveUserCommand(userCommand);

        return "redirect:/index";
    }
}
