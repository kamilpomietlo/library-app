package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.services.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("books/list")
    public String getBooks(Model model) {
        model.addAttribute("books", bookService.getBooks());

        return "books/list";
    }
}
