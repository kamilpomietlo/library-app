package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.commands.AuthorCommand;
import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.services.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("author/list")
    public String getAuthors(Model model) {
        model.addAttribute("authors", authorService.findAll());

        return "author/list";
    }

    @GetMapping("author/find")
    public String authorSearchForm(Model model) {
        model.addAttribute("authors", new Author());

        return "author/find";
    }

    @PostMapping("author/find")
    public String authorSearchSubmit(@ModelAttribute Author author, Model model) {
        model.addAttribute("authors", authorService.findByName(author.getName()));

        return "author/list";
    }

    @GetMapping("author/add")
    public String addNewAuthorForm(Model model) {
        model.addAttribute("authors", new AuthorCommand());

        return "author/add";
    }

    @PostMapping("author/add")
    public String addNewAuthorSubmit(@Valid @ModelAttribute("authors") AuthorCommand authorCommand,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            return "author/add";
        }

        authorService.saveAuthorCommand(authorCommand);

        return "redirect:/author/list";
    }

    @GetMapping("author/{id}/edit")
    public String editAuthorForm(@PathVariable String id, Model model) {
        model.addAttribute("authors", authorService.findCommandById(Long.valueOf(id)));

        return "author/edit";
    }

    @PostMapping("author/{id}/edit")
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
