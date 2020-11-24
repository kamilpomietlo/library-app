package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.model.BookStatus;
import com.kamilpomietlo.libraryapp.services.*;
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
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private final GenreService genreService;
    private final UserService userService;

    public BookController(BookService bookService, AuthorService authorService, PublisherService publisherService,
                          GenreService genreService, UserService userService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.publisherService = publisherService;
        this.genreService = genreService;
        this.userService = userService;
    }

    @GetMapping("book/list")
    public String getBooks(Model model) {
        model.addAttribute("books", bookService.getBooks());

        return "book/list";
    }

    @GetMapping("book/find")
    public String bookSearchForm(Model model) {
        model.addAttribute("books", new Book());

        return "book/find";
    }

    @PostMapping("book/find")
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

    @GetMapping("book/add")
    public String addNewBookForm(Model model) {
        model.addAttribute("authors", authorService.getAuthors());
        model.addAttribute("genres", genreService.getGenres());
        model.addAttribute("publishers", publisherService.getPublishers());
        model.addAttribute("books", new BookCommand());

        return "book/add";
    }

    @PostMapping("book/add")
    public String addNewBookSubmit(Model model, @Valid @ModelAttribute("books") BookCommand bookCommand,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            model.addAttribute("authors", authorService.getAuthors());
            model.addAttribute("genres", genreService.getGenres());
            model.addAttribute("publishers", publisherService.getPublishers());

            return "book/add";
        }

        bookCommand.setBookStatus(BookStatus.AVAILABLE);
        bookService.saveBookCommand(bookCommand);

        return "redirect:/book/list";
    }

    @GetMapping("book/{id}/edit")
    public String editBookForm(@PathVariable String id, Model model) {
        model.addAttribute("authors", authorService.getAuthors());
        model.addAttribute("genres", genreService.getGenres());
        model.addAttribute("publishers", publisherService.getPublishers());
        model.addAttribute("books", bookService.findCommandById(Long.valueOf(id)));

        return "book/edit";
    }

    @PostMapping("book/{id}/edit")
    public String editBookSubmit(Model model, @Valid @ModelAttribute("books") BookCommand bookCommand,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            model.addAttribute("authors", authorService.getAuthors());
            model.addAttribute("genres", genreService.getGenres());
            model.addAttribute("publishers", publisherService.getPublishers());

            return "book/edit";
        }

        BookCommand tempBookCommand = bookService.findCommandById(bookCommand.getId());
        bookCommand.setUserId(tempBookCommand.getUserId());
        bookCommand.setBookStatus(tempBookCommand.getBookStatus());

        bookService.saveBookCommand(bookCommand);

        return "redirect:/book/list";
    }

    @GetMapping("book/{id}/reserve")
    public String reserveBook(@PathVariable String id) {
        BookCommand bookToReserve = bookService.findCommandById(Long.valueOf(id));

        bookService.reserveBook(bookToReserve);

        return "redirect:/index";
    }

    @GetMapping("book/{id}/borrow")
    public String borrowBook(@PathVariable String id) {
        BookCommand bookToBorrow = bookService.findCommandById(Long.valueOf(id));

        bookService.borrowBook(bookToBorrow);

        return "redirect:/index";
    }
}
