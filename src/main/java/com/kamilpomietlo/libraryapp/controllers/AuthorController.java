package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.commands.AuthorCommand;
import com.kamilpomietlo.libraryapp.services.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequestMapping("author")
@Controller
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("list")
    public String getAuthors(Model model) {
        model.addAttribute("authors", authorService.findAll());

        return "author/list";
    }

    @GetMapping("add")
    public String addAuthorForm(Model model) {
        model.addAttribute("authors", new AuthorCommand());

        return "author/add";
    }

    @PostMapping("add")
    public String addAuthorSubmit(@Valid @ModelAttribute("authors") AuthorCommand authorCommand,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            return "author/add";
        }

        authorService.saveAuthorCommand(authorCommand);

        return "redirect:/author/list";
    }

    @GetMapping("{id}/edit")
    public String editAuthorForm(@PathVariable Long id, Model model) {
        model.addAttribute("authors", authorService.findCommandById(id));

        return "author/edit";
    }

    @PostMapping("{id}/edit")
    public String editAuthorSubmit(@Valid @ModelAttribute("authors") AuthorCommand authorCommand,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            return "author/edit";
        }

        authorService.saveAuthorCommand(authorCommand);

        return "redirect:/author/list";
    }
}
