package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.model.BookStatus;
import com.kamilpomietlo.libraryapp.model.Genre;
import com.kamilpomietlo.libraryapp.model.Publisher;
import com.kamilpomietlo.libraryapp.services.AuthorService;
import com.kamilpomietlo.libraryapp.services.BookService;
import com.kamilpomietlo.libraryapp.services.GenreService;
import com.kamilpomietlo.libraryapp.services.PublisherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Slf4j
@RequestMapping("book")
@Controller
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private final GenreService genreService;

    public BookController(BookService bookService, AuthorService authorService, PublisherService publisherService,
                          GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.publisherService = publisherService;
        this.genreService = genreService;
    }

    @GetMapping("list")
    public String getBooks(Model model) {
        model.addAttribute("books", bookService.findAll());

        return "book/list";
    }

    @GetMapping("{id}/delete")
    public String deleteById(@PathVariable Long id) {
        bookService.deleteById(id);

        return "redirect:/book/list";
    }

    @GetMapping("add")
    public String addBookForm(Model model) {
        model.addAttribute("book", new BookCommand());

        return "book/add";
    }

    @PostMapping("add")
    public String addBookSubmit(@Valid @ModelAttribute("book") BookCommand bookCommand,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            bookCommand.getAuthors().clear();

            return "book/add";
        }

        bookCommand.setBookStatus(BookStatus.AVAILABLE);
        bookService.saveBookCommand(bookCommand);

        return "redirect:/book/list";
    }

    @GetMapping("{id}/edit")
    public String editBookForm(@PathVariable String id, Model model) {
        model.addAttribute("book", bookService.findCommandById(Long.valueOf(id)));

        return "book/edit";
    }

    @PostMapping("{id}/edit")
    public String editBookSubmit(@Valid @ModelAttribute("book") BookCommand bookCommand,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            return "book/edit";
        }

        BookCommand tempBookCommand = bookService.findCommandById(bookCommand.getId());
        bookCommand.setUserId(tempBookCommand.getUserId());
        bookCommand.setBookStatus(tempBookCommand.getBookStatus());

        bookService.saveBookCommand(bookCommand);

        return "redirect:/book/list";
    }

    @GetMapping("{id}/reserve")
    public String reserveBook(@PathVariable String id) {
        BookCommand bookToReserve = bookService.findCommandById(Long.valueOf(id));

        bookService.reserveBook(bookToReserve);

        return "redirect:/index";
    }

    @GetMapping("reservations")
    public String getReservations(Model model) {
        model.addAttribute("books", bookService.getReservedBooks());

        return "book/reservations";
    }

    @GetMapping("{id}/accept")
    public String acceptBorrowingBook(@PathVariable String id) {
        BookCommand bookToBorrow = bookService.findCommandById(Long.valueOf(id));

        bookService.acceptBorrowingBook(bookToBorrow);

        return "redirect:/index";
    }

    @ModelAttribute("authors")
    private Set<Author> getAuthors() {
        return authorService.findAll();
    }

    @ModelAttribute("publishers")
    private Set<Publisher> getPublishers() {
        return publisherService.findAll();
    }

    @ModelAttribute("genres")
    private Set<Genre> getGenres() {
        return genreService.findAll();
    }
}
