package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.model.BookStatus;
import com.kamilpomietlo.libraryapp.model.Publisher;
import com.kamilpomietlo.libraryapp.services.AuthorService;
import com.kamilpomietlo.libraryapp.services.BookService;
import com.kamilpomietlo.libraryapp.services.PublisherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller related to {@code Book} object.
 */
@Slf4j
@RequestMapping("book")
@Controller
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final PublisherService publisherService;

    public BookController(BookService bookService, AuthorService authorService, PublisherService publisherService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.publisherService = publisherService;
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

    /**
     * Submits form data for adding new object and saves it if validation is successful.
     *
     * @param bookCommand object to be added
     * @param bindingResult performs validation of the object
     * @return add form when validation failed, otherwise redirects to list of books
     */
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
    public String editBookForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.findCommandById(id));

        return "book/edit";
    }

    /**
     * Submits form data for editing existing object and saves it if validation is successful. Fields not sent
     * via form are set to current ones before saving object.
     *
     * @param bookCommand object to be edited
     * @param bindingResult performs validation of the object
     * @return edit form when validation failed, otherwise redirects to list of books
     */
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
        bookCommand.setDateOfReserveOrBorrow(tempBookCommand.getDateOfReserveOrBorrow());
        bookCommand.setDeadlineDate(tempBookCommand.getDeadlineDate());

        bookService.saveBookCommand(bookCommand);

        return "redirect:/book/list";
    }

    /**
     * Reserves the book by {@code id}.
     *
     * @param id id of the book to be reserved
     * @return redirects to user account page
     */
    @GetMapping("{id}/reserve")
    public String reserveBook(@PathVariable Long id) {
        BookCommand bookToReserve = bookService.findCommandById(id);

        bookService.reserveBook(bookToReserve);

        return "redirect:/user/account";
    }

    @GetMapping("reservations")
    public String getReservations(Model model) {
        model.addAttribute("books", bookService.getReservedBooks());

        return "book/reservations";
    }

    @GetMapping("{id}/accept-reservation")
    public String acceptReservation(@PathVariable Long id) {
        bookService.acceptBorrowingBook(id);

        return "redirect:/book/reservations";
    }

    @GetMapping("borrowed")
    public String getBorrowedBooks(Model model) {
        model.addAttribute("books", bookService.getBorrowedBooks());

        return "book/borrowed";
    }

    @GetMapping("{id}/accept-returning")
    public String acceptReturning(@PathVariable Long id) {
        bookService.acceptReturningBook(id);

        return "redirect:/book/borrowed";
    }

    @GetMapping("{id}/prolong")
    public String prolongBook(@PathVariable Long id) {
        bookService.prolongBook(id);

        return "redirect:/user/account";
    }

    @ModelAttribute("authors")
    private List<Author> getAuthors() {
        return authorService.findAll();
    }

    @ModelAttribute("publishers")
    private List<Publisher> getPublishers() {
        return publisherService.findAll();
    }
}
