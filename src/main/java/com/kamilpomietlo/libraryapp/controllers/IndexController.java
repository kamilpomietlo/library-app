package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.commands.UserRegisterCommand;
import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.model.ConfirmationToken;
import com.kamilpomietlo.libraryapp.services.ConfirmationTokenService;
import com.kamilpomietlo.libraryapp.services.IndexService;
import com.kamilpomietlo.libraryapp.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
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
        model.addAttribute("books", new Book());
        model.addAttribute("authors", new Author());

        return "index";
    }

    @PostMapping({"index", ""})
    public String searchSubmit(@ModelAttribute Book book, @ModelAttribute Author author, Model model) {
        model.addAttribute("books", indexService.searchByBookAndAuthor(book, author));

        return "book/list";
    }

    @GetMapping("register")
    public String registerUser(Model model) {
        model.addAttribute("user", new UserRegisterCommand());

        return "register";
    }

    @PostMapping("register")
    public String registerUser(@Valid @ModelAttribute("user") UserRegisterCommand userRegisterCommand, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            return "register";
        }

        userService.registerUser(userRegisterCommand);

        return "redirect:/login";
    }

    @GetMapping("register/confirm")
    public String confirmMail(@RequestParam("token") String token) {
        Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenService.findConfirmationTokenByToken(token);
        optionalConfirmationToken.ifPresent(userService::confirmUser);

        return "redirect:/login";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }
}
