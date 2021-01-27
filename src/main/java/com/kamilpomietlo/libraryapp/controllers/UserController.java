package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.commands.UserCommand;
import com.kamilpomietlo.libraryapp.model.ConfirmationToken;
import com.kamilpomietlo.libraryapp.services.ConfirmationTokenService;
import com.kamilpomietlo.libraryapp.services.MyUserDetailsService;
import com.kamilpomietlo.libraryapp.services.UserService;
import com.kamilpomietlo.libraryapp.validations.EditInfo;
import com.kamilpomietlo.libraryapp.validations.RegisterInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller related to {@code User} object.
 */
@Slf4j
@RequestMapping("user")
@Controller
public class UserController {

    private final UserService userService;
    private final MyUserDetailsService myUserDetailsService;
    private final ConfirmationTokenService confirmationTokenService;

    public UserController(UserService userService, MyUserDetailsService myUserDetailsService,
                          ConfirmationTokenService confirmationTokenService) {
        this.userService = userService;
        this.myUserDetailsService = myUserDetailsService;
        this.confirmationTokenService = confirmationTokenService;
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

    /**
     * Provides user account page for the currently logged user.
     *
     * @param model model
     * @return user account page
     */
    @GetMapping("account")
    public String getAccountInfo(Model model) {
        Long userId = myUserDetailsService.getLoggedAccountId();

        model.addAttribute("user", userService.findCommandById(userId));

        return "user/account";
    }

    /**
     * Provides user edit page for the currently logged user.
     *
     * @param model model
     * @return user edit page
     */
    @GetMapping("edit")
    public String editUserForm(Model model) {
         Long id = myUserDetailsService.getLoggedAccountId();

         model.addAttribute("user", userService.findCommandById(id));

         return "user/edit";
    }

    /**
     * Submits form data for editing existing object and saves it if validation is successful. Fields not sent
     * via form are set to current ones before saving object.
     *
     * @param userCommand object to be edited
     * @param bindingResult performs validation of the object
     * @return edit form when validation failed, otherwise redirects to list of publishers
     */
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

    @GetMapping("register")
    public String registerUserGet(Model model) {
        model.addAttribute("user", new UserCommand());

        return "user/register";
    }

    /**
     * Submits form data for registering new user and saves it if validation is successful.
     *
     * @param userCommand object to be saved
     * @param bindingResult performs validation of the object
     * @return registration form when validation failed, otherwise register-info page
     */
    @PostMapping("register")
    public String registerUserPost(@Validated(RegisterInfo.class) @ModelAttribute("user") UserCommand userCommand,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            return "user/register";
        }

        userService.registerUser(userCommand);

        return "user/register-info";
    }

    /**
     * Confirms user registration if provided {@code ConfirmationToken} is correct.
     *
     * @param token confirmationToken
     * @return login page
     */
    @GetMapping("register/confirm")
    public String confirmMail(@RequestParam("token") String token) {
        Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenService.findConfirmationTokenByToken(token);
        optionalConfirmationToken.ifPresent(userService::confirmUser);

        return "redirect:/login";
    }
}
