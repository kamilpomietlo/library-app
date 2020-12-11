package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.model.ConfirmationToken;
import com.kamilpomietlo.libraryapp.model.User;
import com.kamilpomietlo.libraryapp.services.ConfirmationTokenService;
import com.kamilpomietlo.libraryapp.services.IndexService;
import com.kamilpomietlo.libraryapp.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class IndexController {

    private final IndexService indexService;
    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;

    public IndexController(IndexService indexService, UserService userService, ConfirmationTokenService confirmationTokenService) {
        this.indexService = indexService;
        this.userService = userService;
        this.confirmationTokenService = confirmationTokenService;
    }

    @GetMapping({"index", ""})
    public String searchForm(Model model) {
        model.addAttribute("books", new Book());
        model.addAttribute("authors", new Author());

        return "index";
    }

    @PostMapping({"index", ""})
    public String searchSubmit(@ModelAttribute Book book, @ModelAttribute Author author, Model model) {
        model.addAttribute("books", indexService.searchByBookAndAuthor(book, author));

        return "book/list";
    }

    @GetMapping("sign-in")
    public String signIn(Model model) {
        model.addAttribute("user", new User());

        return "redirect:/index";
    }

    @GetMapping("sign-up")
    public String signUp(Model model) {
        model.addAttribute("user", new User());

        return "sign-up";
    }

    @PostMapping("sign-up")
    public String signUp(User user) {
        userService.signUpUser(user);

        return "redirect:/sign-in";
    }

    @GetMapping("sign-up/confirm")
    public String confirmMail(@RequestParam("token") String token) {
        Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenService.findConfirmationTokenByToken(token);
        optionalConfirmationToken.ifPresent(userService::confirmUser);

        return "sign-in";
    }

    //todo sign-up on the same email, add first and last name, prevent from reserve when user not enabled
    //todo use UserCommand, update converters, update bootstrap users and add one admin account there
}
