package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.commands.BookCommand;
import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.model.Genre;
import com.kamilpomietlo.libraryapp.model.Publisher;
import com.kamilpomietlo.libraryapp.services.AuthorService;
import com.kamilpomietlo.libraryapp.services.BookService;
import com.kamilpomietlo.libraryapp.services.GenreService;
import com.kamilpomietlo.libraryapp.services.PublisherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;

@Controller
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private final GenreService genreService;

    public BookController(BookService bookService, AuthorService authorService,
                          PublisherService publisherService, GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.publisherService = publisherService;
        this.genreService = genreService;
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
