package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.model.Genre;
import com.kamilpomietlo.libraryapp.services.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller used for index page activities.
 */
@Slf4j
@Controller
public class IndexController {

    private final IndexService indexService;

    public IndexController(IndexService indexService) {
        this.indexService = indexService;
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

    @GetMapping("login")
    public String login() {
        return "login";
    }
}
