package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.commands.AuthorCommand;
import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.services.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("author/list")
    public String getAuthors(Model model) {
        model.addAttribute("authors", authorService.getAuthors());

        return "author/list";
    }

    @GetMapping("author/find")
    public String authorSearchForm(Model model) {
        model.addAttribute("authors", new Author());

        return "author/find";
    }

    @PostMapping("author/find")
    public String authorSearchSubmit(@ModelAttribute Author author, Model model) {
        model.addAttribute("authors", authorService.findByFirstNameAndLastName(author.getFirstName(), author.getLastName()));

        return "author/list";
    }

    @GetMapping("author/add")
    public String addNewAuthorForm(Model model) {
        model.addAttribute("authors", new AuthorCommand());

        return "author/add";
    }

    @PostMapping("author/add")
    public String addNewAuthorSubmit(@ModelAttribute AuthorCommand authorCommand) {
        authorService.saveAuthorCommand(authorCommand);

        return "redirect:/author/list";
    }

    @GetMapping("author/{id}/edit")
    public String editAuthorForm(@PathVariable String id, Model model) {
        model.addAttribute("authors", authorService.findCommandById(Long.valueOf(id)));

        return "author/edit";
    }

    @PostMapping("author/{id}/edit")
    public String editAuthorSubmit(@ModelAttribute AuthorCommand authorCommand) {
        authorService.saveAuthorCommand(authorCommand);

        return "redirect:/author/list";
    }
}
