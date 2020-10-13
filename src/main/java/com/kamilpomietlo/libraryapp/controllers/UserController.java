package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.commands.UserCommand;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.model.User;
import com.kamilpomietlo.libraryapp.services.BookService;
import com.kamilpomietlo.libraryapp.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;

@Controller
public class UserController {

    private final UserService userService;
    private final BookService bookService;

    public UserController(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
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

    @GetMapping("user/{id}/edit")
    public String editUserForm(@PathVariable String id, Model model) {
        Set<Book> books = bookService.getBooks();
        model.addAttribute("books", books);

        model.addAttribute("users", userService.findCommandById(Long.valueOf(id)));

        return "user/edit";
    }

    @PostMapping("user/{id}/edit")
    public String editUserSubmit(@ModelAttribute UserCommand userCommand) {
        userService.saveUserCommand(userCommand);

        return "redirect:/user/list";
    }
}
