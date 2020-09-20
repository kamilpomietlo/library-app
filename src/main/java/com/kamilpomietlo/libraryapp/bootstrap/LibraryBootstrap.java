package com.kamilpomietlo.libraryapp.bootstrap;

import com.kamilpomietlo.libraryapp.model.*;
import com.kamilpomietlo.libraryapp.repositories.BookRepository;
import com.kamilpomietlo.libraryapp.repositories.GenreRepository;
import com.kamilpomietlo.libraryapp.repositories.UserRepository;
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
    private final UserRepository userRepository;
    private final String EXCEPTION_STRING = "Expected object not found.";

    public LibraryBootstrap(BookRepository bookRepository, GenreRepository genreRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        bookRepository.saveAll(loadBooks());
        userRepository.saveAll(loadUsers());
        log.debug("Bootstrap data loaded.");
    }

    private List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();


        // get genres
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
        Genre fantasyGenre = fantasyGenreOptional.get();
        Genre horrorGenre = horrorGenreOptional.get();

        // add authors, publishers and books
        Author brandonSanderson = new Author("Brandon", "Sanderson");
        Publisher torBooks = new Publisher("Tor Books");

        Book elantris = new Book();
        elantris.setTitle("Elantris");
        elantris.addAuthor(brandonSanderson);
        elantris.setGenre(fantasyGenre);
        elantris.addPublisher(torBooks);
        elantris.setCoverType(CoverType.SOFT);
        elantris.setYearOfRelease(2005);
        elantris.setIsbn("9788374806671");
        books.add(elantris);

        Author stephenKing = new Author("Stephen", "King");
        Publisher vikingPress = new Publisher("Viking Press");

        Book misery = new Book();
        misery.setTitle("Misery");
        misery.addAuthor(stephenKing);
        misery.setGenre(horrorGenre);
        misery.addPublisher(vikingPress);
        misery.setCoverType(CoverType.SOFT);
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
        bloodOfElves.setCoverType(CoverType.SOFT);
        bloodOfElves.setYearOfRelease(1994);
        bloodOfElves.setIsbn("9788375780659");
        books.add(bloodOfElves);

        Book timeOfContempt = new Book();
        timeOfContempt.setTitle("Time of Contempt");
        timeOfContempt.addAuthor(andrzejSapkowski);
        timeOfContempt.setGenre(fantasyGenre);
        timeOfContempt.addPublisher(superNowa);
        timeOfContempt.setCoverType(CoverType.SOFT);
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
        poirotInvestigates.setCoverType(CoverType.HARD);
        poirotInvestigates.setYearOfRelease(1924);
        poirotInvestigates.setIsbn("9788577991273");
        books.add(poirotInvestigates);

        Author terryPratchett = new Author("Terry", "Pratchett");
        Author neilGaiman = new Author("Neil", "Gaiman");
        Publisher gollancz = new Publisher("Gollancz");

        Book goodOmens = new Book();
        goodOmens.setTitle("Good Omens");
        goodOmens.addAuthor(terryPratchett);
        goodOmens.addAuthor(neilGaiman);
        goodOmens.setGenre(fantasyGenre);
        goodOmens.addPublisher(gollancz);
        goodOmens.setCoverType(CoverType.HARD);
        goodOmens.setYearOfRelease(1990);
        goodOmens.setIsbn("9780575048003");
        books.add(goodOmens);

        return books;
    }

    private List<User> loadUsers() {
        List<User> users = new ArrayList<>();

        // get books
        Optional<Book> miseryBookOptional = bookRepository.findByTitle("Misery");
        if (miseryBookOptional.isEmpty()) {
            throw new RuntimeException(EXCEPTION_STRING);
        }

        Optional<Book> timeOfContemptBookOptional = bookRepository.findByTitle("Time of Contempt");
        if (timeOfContemptBookOptional.isEmpty()) {
            throw new RuntimeException(EXCEPTION_STRING);
        }

        Optional<Book> poirotInvestigatesBookOptional = bookRepository.findByTitle("Poirot Investigates");
        if (poirotInvestigatesBookOptional.isEmpty()) {
            throw new RuntimeException(EXCEPTION_STRING);
        }

        // get optionals
        Book miseryBook = miseryBookOptional.get();
        Book timeOfContemptBook = timeOfContemptBookOptional.get();
        Book poirotInvestigatesBook = poirotInvestigatesBookOptional.get();

        User janKowalski = new User();
        janKowalski.setFirstName("Jan");
        janKowalski.setLastName("Kowalski");
        janKowalski.setIdNumber("88021574862");
        janKowalski.setCountry("Poland");
        janKowalski.setState("mazowieckie");
        janKowalski.setCity("Warszawa");
        janKowalski.setStreet("Szkolna");
        janKowalski.setHomeNumber("7");
        janKowalski.addBook(miseryBook);
        users.add(janKowalski);

        User tomaszNowak = new User();
        tomaszNowak.setFirstName("Tomasz");
        tomaszNowak.setLastName("Nowak");
        tomaszNowak.setIdNumber("74012285943");
        tomaszNowak.setCountry("Poland");
        tomaszNowak.setState("śląskie");
        tomaszNowak.setCity("Katowice");
        tomaszNowak.setStreet("Rynkowa");
        tomaszNowak.setHomeNumber("12");
        // todo: fix - it sees the book added to the user, the second one loses author
        // (it takes the author with it?)
        tomaszNowak.addBook(timeOfContemptBook);
        tomaszNowak.addBook(poirotInvestigatesBook);
        users.add(tomaszNowak);

        return users;
    }
}
