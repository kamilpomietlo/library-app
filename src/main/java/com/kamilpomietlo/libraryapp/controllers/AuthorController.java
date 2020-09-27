package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.services.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @RequestMapping("author/list")
    public String getAuthors(Model model) {
        model.addAttribute("authors", authorService.getAuthors());

        return "author/list";
    }

    @GetMapping("author/search")
    public String authorSearchForm(Model model) {
        model.addAttribute("searchedAuthor", new Author());

        return "author/search";
    }

    @PostMapping("author/search")
    public String authorSearchSubmit(@ModelAttribute Author author, Model model) {
        model.addAttribute("authors", authorService.findByFirstNameAndLastName(author.getFirstName(), author.getLastName()));

        return "author/list";
    }
}
