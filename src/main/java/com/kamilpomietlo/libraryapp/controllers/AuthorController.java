package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.services.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @RequestMapping("authors/list")
    public String getAuthors(Model model) {
        model.addAttribute("authors", authorService.getAuthors());

        return "authors/list";
    }
}
