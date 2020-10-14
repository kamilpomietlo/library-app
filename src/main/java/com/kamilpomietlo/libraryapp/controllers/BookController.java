package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.model.*;
import com.kamilpomietlo.libraryapp.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;

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
        Set<Author> authors = authorService.getAuthors();
        model.addAttribute("authors", authors);

        Set<Genre> genres = genreService.getGenres();
        model.addAttribute("genres", genres);

        Set<Publisher> publishers = publisherService.getPublishers();
        model.addAttribute("publishers", publishers);

        model.addAttribute("books", new BookCommand());

        return "book/add";
    }

    @PostMapping("book/add")
    public String addNewBookSubmit(@ModelAttribute BookCommand bookCommand) {
        bookCommand.setBookStatus(BookStatus.AVAILABLE);

        bookService.saveBookCommand(bookCommand);

        return "redirect:/book/list";
    }

    @GetMapping("book/{id}/edit")
    public String editBookForm(@PathVariable String id, Model model) {
        Set<Author> authors = authorService.getAuthors();
        model.addAttribute("authors", authors);

        Set<Genre> genres = genreService.getGenres();
        model.addAttribute("genres", genres);

        Set<Publisher> publishers = publisherService.getPublishers();
        model.addAttribute("publishers", publishers);

        Set<User> users = userService.getUsers();
        model.addAttribute("users", users);

        model.addAttribute("books", bookService.findCommandById(Long.valueOf(id)));

        return "book/edit";
    }

    @PostMapping("book/{id}/edit")
    public String editBookSubmit(@ModelAttribute BookCommand bookCommand) {
        BookCommand tempBookCommand = bookService.findCommandById(bookCommand.getId());
        bookCommand.setUserId(tempBookCommand.getUserId());
        bookCommand.setBookStatus(tempBookCommand.getBookStatus());

        bookService.saveBookCommand(bookCommand);

        return "redirect:/book/list";
    }

//    @GetMapping("book/{id}/reserve")
//    public String reserveBook(@PathVariable String id) {
//        bookService.reserveBook(Long.valueOf(id));
//
//        return "redirect:/book/list";
//    }
}
