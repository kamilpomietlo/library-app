package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.commands.UserCommand;
import com.kamilpomietlo.libraryapp.services.MyUserDetailsService;
import com.kamilpomietlo.libraryapp.services.UserService;
import com.kamilpomietlo.libraryapp.validations.EditInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("list")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.findAll());

        return "user/list";
    }

    @GetMapping("{id}/delete")
    public String deleteById(@PathVariable Long id) {
        userService.deleteById(id);

        return "redirect:/user/list";
    }

    @GetMapping("account")
    public String getAccountInfo(Model model) {
        Long userId = myUserDetailsService.getLoggedAccountId();

        model.addAttribute("user", userService.findCommandById(userId));

        return "user/account";
    }

    @GetMapping("edit")
    public String editUserForm(Model model) {
         Long id = myUserDetailsService.getLoggedAccountId();

         model.addAttribute("user", userService.findCommandById(id));

         return "user/edit";
    }

    @PostMapping("edit")
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
