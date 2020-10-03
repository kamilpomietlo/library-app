package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.services.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("book/list")
    public String getBooks(Model model) {
        model.addAttribute("books", bookService.getBooks());

        return "book/list";
    }

    @GetMapping("book/search")
    public String bookSearchForm(Model model) {
        model.addAttribute("books", new Book());

        return "book/search";
    }

    @PostMapping("book/search")
    public String bookSearchSubmit(@ModelAttribute Book book, Model model) {
        model.addAttribute("books", bookService.findByTitle(book.getTitle()));

        return "book/list";
    }

    @GetMapping("book/delete")
    public String deleteByIdForm(Model model) {
        model.addAttribute("books", new Book());

        return "book/delete";
    }

    @PostMapping("book/delete")
    public String deleteByIdSubmit(@ModelAttribute Book book) {
        bookService.deleteById(book.getId());

        return "redirect:/book/list";
    }

    @PostMapping("book/{id}/reserve")
    public String reserveBook(@PathVariable String id, Model model) {
        model.addAttribute("books", bookService.getBooks());

        return "book/list";
    }
}
