package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.commands.UserCommand;
import com.kamilpomietlo.libraryapp.model.User;
import com.kamilpomietlo.libraryapp.services.MyUserDetailsService;
import com.kamilpomietlo.libraryapp.services.UserService;
import com.kamilpomietlo.libraryapp.validations.EditInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@RequestMapping("user")
@Controller
public class UserController {

    private final UserService userService;
    private final MyUserDetailsService myUserDetailsService;

    public UserController(UserService userService, MyUserDetailsService myUserDetailsService) {
        this.userService = userService;
        this.myUserDetailsService = myUserDetailsService;
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

    @GetMapping("/account")
    public String getAccountInfo(Model model) {
        Long userId = myUserDetailsService.getLoggedAccountId();

        model.addAttribute("user", userService.findCommandById(userId));

        return "user/account";
    }

    @GetMapping("/edit")
    public String editUserForm(Model model) {
         Long id = myUserDetailsService.getLoggedAccountId();

         model.addAttribute("user", userService.findCommandById(id));

         return "user/edit";
    }

    @PostMapping("/edit")
    public String editUserSubmit(@Validated(EditInfo.class) @ModelAttribute("user") UserCommand userCommand,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            return "user/edit";
        }

        UserCommand userCommandToUpdate = userService.editRemainingFields(userCommand);
        userService.saveUserCommand(userCommandToUpdate);

        return "redirect:/user/account";
    }
}
