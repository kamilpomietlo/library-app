package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.services.AuthorService;
import com.kamilpomietlo.libraryapp.services.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {

    private final BookService bookService;
    private final AuthorService authorService;

    public IndexController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping({"index", ""})
    public String searchForm(Model model) {
        model.addAttribute("books", new Book());
        model.addAttribute("authors", new Author());

        return "index";
    }

    @PostMapping({"index", ""})
    public String searchSubmit(@ModelAttribute Book book, @ModelAttribute Author author, Model model) {
        model.addAttribute("books", bookService.findByTitle(book.getTitle()));
        model.addAttribute("authors", authorService.findByName(author.getName()));

        return "book/list";
    }
}
