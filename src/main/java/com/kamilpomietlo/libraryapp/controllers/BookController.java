package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.services.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("books/search")
    public String bookSearchForm(Model model) {
        model.addAttribute("searchedBook", new Book());

        return "books/search";
    }

    @PostMapping("books/search")
    public String bookSearchSubmit(@ModelAttribute Book book, Model model) {
        model.addAttribute("books", bookService.findByTitle(book.getTitle()));

        return "books/list";
    }
}
