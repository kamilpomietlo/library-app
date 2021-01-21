package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.commands.UserCommand;
import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.model.ConfirmationToken;
import com.kamilpomietlo.libraryapp.model.Genre;
import com.kamilpomietlo.libraryapp.services.ConfirmationTokenService;
import com.kamilpomietlo.libraryapp.services.IndexService;
import com.kamilpomietlo.libraryapp.services.UserService;
import com.kamilpomietlo.libraryapp.validations.RegisterInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Slf4j
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
        model.addAttribute("book", new Book());
        model.addAttribute("author", new Author());
        model.addAttribute("genre", Genre.values());

        return "index";
    }

    @PostMapping({"index", ""})
    public String searchSubmit(@ModelAttribute("book") Book book, @ModelAttribute("author") Author author,
                               Genre genre, Model model) {
        model.addAttribute("books", indexService.findBooks(book, author, genre));

        return "book/list";
    }

    @GetMapping("register")
    public String registerUserGet(Model model) {
        model.addAttribute("user", new UserCommand());

        return "register";
    }

    @PostMapping("register")
    public String registerUserPost(@Validated(RegisterInfo.class) @ModelAttribute("user") UserCommand userCommand,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            return "register";
        }

        userService.registerUser(userCommand);

        return "register-info";
    }

    @GetMapping("register/confirm")
    public String confirmMail(@RequestParam("token") String token) {
        Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenService.findConfirmationTokenByToken(token);
        optionalConfirmationToken.ifPresent(userService::confirmUser);

        return "login";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }
}
