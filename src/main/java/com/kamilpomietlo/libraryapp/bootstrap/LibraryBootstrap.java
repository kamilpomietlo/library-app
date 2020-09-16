package com.kamilpomietlo.libraryapp.bootstrap;

import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.model.Book;
import com.kamilpomietlo.libraryapp.model.Genre;
import com.kamilpomietlo.libraryapp.model.Publisher;
import com.kamilpomietlo.libraryapp.repositories.BookRepository;
import com.kamilpomietlo.libraryapp.repositories.GenreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class LibraryBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    public LibraryBootstrap(BookRepository bookRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        bookRepository.saveAll(getBooks());
        log.debug("Bootstrap data loaded.");
    }

    private List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        final String EXCEPTION_STRING = "Expected genre not found.";

        // get Genres
        Optional<Genre> thrillerGenreOptional = genreRepository.findByDescription("Thriller");
        if (thrillerGenreOptional.isEmpty()) {
            throw new RuntimeException(EXCEPTION_STRING);
        }

        Optional<Genre> romanceGenreOptional = genreRepository.findByDescription("Romance");
        if (romanceGenreOptional.isEmpty()) {
            throw new RuntimeException(EXCEPTION_STRING);
        }

        Optional<Genre> scienceFictionGenreOptional = genreRepository.findByDescription("Science fiction");
        if (scienceFictionGenreOptional.isEmpty()) {
            throw new RuntimeException(EXCEPTION_STRING);
        }

        Optional<Genre> fantasyGenreOptional = genreRepository.findByDescription("Fantasy");
        if (fantasyGenreOptional.isEmpty()) {
            throw new RuntimeException(EXCEPTION_STRING);
        }

        Optional<Genre> mysteryGenreOptional = genreRepository.findByDescription("Mystery");
        if (mysteryGenreOptional.isEmpty()) {
            throw new RuntimeException(EXCEPTION_STRING);
        }

        Optional<Genre> horrorGenreOptional = genreRepository.findByDescription("Horror");
        if (horrorGenreOptional.isEmpty()) {
            throw new RuntimeException(EXCEPTION_STRING);
        }

        Optional<Genre> historyGenreOptional = genreRepository.findByDescription("History");
        if (historyGenreOptional.isEmpty()) {
            throw new RuntimeException(EXCEPTION_STRING);
        }

        // get optionals
        Genre thrillerGenre = thrillerGenreOptional.get();
        Genre romanceGenre = romanceGenreOptional.get();
        Genre scienceFictionGenre = scienceFictionGenreOptional.get();
        Genre fantasyGenre = fantasyGenreOptional.get();
        Genre mysteryGenre = mysteryGenreOptional.get();
        Genre horrorGenre = horrorGenreOptional.get();
        Genre historyGenre = historyGenreOptional.get();

        // set books
        Author brandonSanderson = new Author("Brandon", "Sanderson");
        Publisher torBooks = new Publisher("Tor Books");

        Book elantris = new Book();
        elantris.setTitle("Elantris");
        elantris.addAuthor(brandonSanderson);
        elantris.setGenre(fantasyGenre);
        elantris.addPublisher(torBooks);
        elantris.setYearOfRelease(2005);
        elantris.setIsbn("0765311771");
        books.add(elantris);

        Author stephenKing = new Author("Stephen", "King");
        Publisher vikingPress = new Publisher("Viking Press");

        Book misery = new Book();
        misery.setTitle("Misery");
        misery.addAuthor(stephenKing);
        misery.setGenre(horrorGenre);
        misery.addPublisher(vikingPress);
        misery.setYearOfRelease(1987);
        misery.setIsbn("9780670813643");
        books.add(misery);

        Author andrzejSapkowski = new Author("Andrzej", "Sapkowski");
        Publisher superNowa = new Publisher("superNOWA");

        Book bloodOfElves = new Book();
        bloodOfElves.setTitle("Blood of Elves");
        bloodOfElves.addAuthor(andrzejSapkowski);
        bloodOfElves.setGenre(fantasyGenre);
        bloodOfElves.addPublisher(superNowa);
        bloodOfElves.setYearOfRelease(1994);
        bloodOfElves.setIsbn("9788375780659");
        books.add(bloodOfElves);

        Book timeOfContempt = new Book();
        timeOfContempt.setTitle("Time of Contempt");
        timeOfContempt.addAuthor(andrzejSapkowski);
        timeOfContempt.setGenre(fantasyGenre);
        timeOfContempt.addPublisher(superNowa);
        timeOfContempt.setYearOfRelease(1995);
        timeOfContempt.setIsbn("9788375780666");
        books.add(timeOfContempt);

        Author agathaChristie = new Author("Agatha", "Christie");
        Publisher bodleyHead = new Publisher("Bodley Head");

        Book poirotInvestigates = new Book();
        poirotInvestigates.setTitle("Poirot Investigates");
        poirotInvestigates.addAuthor(agathaChristie);
        poirotInvestigates.setGenre(thrillerGenre);
        poirotInvestigates.addPublisher(bodleyHead);
        poirotInvestigates.setYearOfRelease(1924);
        poirotInvestigates.setIsbn("9788577991273");
        books.add(poirotInvestigates);

        return books;
    }
}
