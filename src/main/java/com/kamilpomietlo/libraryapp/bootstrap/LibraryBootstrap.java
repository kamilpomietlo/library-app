package com.kamilpomietlo.libraryapp.bootstrap;

import com.kamilpomietlo.libraryapp.model.*;
import com.kamilpomietlo.libraryapp.repositories.*;
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

    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public LibraryBootstrap(GenreRepository genreRepository, AuthorRepository authorRepository,
                            PublisherRepository publisherRepository, BookRepository bookRepository,
                            UserRepository userRepository) {
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadBooks();
        log.debug("Bootstrap data loaded.");
    }

    private void loadBooks() {
        List<Author> authors = new ArrayList<>();
        List<Publisher> publishers = new ArrayList<>();
        List<Book> books = new ArrayList<>();
        List<User> users = new ArrayList<>();
        String EXCEPTION_STRING = "Expected object not found.";

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

        // add authors
        Author brandonSanderson = new Author("Brandon Sanderson");
        brandonSanderson.setId(1L);

        Author stephenKing = new Author("Stephen King");
        stephenKing.setId(2L);

        Author andrzejSapkowski = new Author("Andrzej Sapkowski");
        andrzejSapkowski.setId(3L);

        Author agathaChristie = new Author("Agatha Christie");
        agathaChristie.setId(4L);

        Author terryPratchett = new Author("Terry Pratchett");
        terryPratchett.setId(5L);

        Author neilGaiman = new Author("Neil Gaiman");
        neilGaiman.setId(6L);

        authors.add(brandonSanderson);
        authors.add(stephenKing);
        authors.add(andrzejSapkowski);
        authors.add(agathaChristie);
        authors.add(terryPratchett);
        authors.add(neilGaiman);
        authorRepository.saveAll(authors);

        // add publishers
        Publisher torBooks = new Publisher("Tor Books");
        torBooks.setId(1L);

        Publisher vikingPress = new Publisher("Viking Press");
        vikingPress.setId(2L);

        Publisher superNowa = new Publisher("superNOWA");
        superNowa.setId(3L);

        Publisher bodleyHead = new Publisher("Bodley Head");
        bodleyHead.setId(4L);

        Publisher gollancz = new Publisher("Gollancz");
        gollancz.setId(5L);

        publishers.add(torBooks);
        publishers.add(vikingPress);
        publishers.add(superNowa);
        publishers.add(bodleyHead);
        publishers.add(gollancz);
        publisherRepository.saveAll(publishers);

        // add books
        Book elantris = new Book();
        elantris.setId(1L);
        elantris.setTitle("Elantris");
        elantris.addAuthor(brandonSanderson);
        elantris.setGenre(fantasyGenre);
        elantris.addPublisher(torBooks);
        elantris.setCoverType(CoverType.SOFT);
        elantris.setYearOfRelease(2005);
        elantris.setIsbn("9788374806671");
        elantris.setBookStatus(BookStatus.AVAILABLE);

        Book misery = new Book();
        misery.setId(2L);
        misery.setTitle("Misery");
        misery.addAuthor(stephenKing);
        misery.setGenre(horrorGenre);
        misery.addPublisher(vikingPress);
        misery.setCoverType(CoverType.SOFT);
        misery.setYearOfRelease(1987);
        misery.setIsbn("9780670813643");
        misery.setBookStatus(BookStatus.RESERVED);

        Book bloodOfElves = new Book();
        bloodOfElves.setId(3L);
        bloodOfElves.setTitle("Blood of Elves");
        bloodOfElves.addAuthor(andrzejSapkowski);
        bloodOfElves.setGenre(fantasyGenre);
        bloodOfElves.addPublisher(superNowa);
        bloodOfElves.setCoverType(CoverType.SOFT);
        bloodOfElves.setYearOfRelease(1994);
        bloodOfElves.setIsbn("9788375780659");
        bloodOfElves.setBookStatus(BookStatus.AVAILABLE);

        Book timeOfContempt = new Book();
        timeOfContempt.setId(4L);
        timeOfContempt.setTitle("Time of Contempt");
        timeOfContempt.addAuthor(andrzejSapkowski);
        timeOfContempt.setGenre(fantasyGenre);
        timeOfContempt.addPublisher(superNowa);
        timeOfContempt.setCoverType(CoverType.SOFT);
        timeOfContempt.setYearOfRelease(1995);
        timeOfContempt.setIsbn("9788375780666");
        timeOfContempt.setBookStatus(BookStatus.BORROWED);

        Book poirotInvestigates = new Book();
        poirotInvestigates.setId(5L);
        poirotInvestigates.setTitle("Poirot Investigates");
        poirotInvestigates.addAuthor(agathaChristie);
        poirotInvestigates.setGenre(thrillerGenre);
        poirotInvestigates.addPublisher(bodleyHead);
        poirotInvestigates.setCoverType(CoverType.HARD);
        poirotInvestigates.setYearOfRelease(1924);
        poirotInvestigates.setIsbn("9788577991273");
        poirotInvestigates.setBookStatus(BookStatus.RESERVED);

        Book goodOmens = new Book();
        goodOmens.setId(6L);
        goodOmens.setTitle("Good Omens");
        goodOmens.addAuthor(terryPratchett);
        goodOmens.addAuthor(neilGaiman);
        goodOmens.setGenre(fantasyGenre);
        goodOmens.addPublisher(gollancz);
        goodOmens.setCoverType(CoverType.HARD);
        goodOmens.setYearOfRelease(1990);
        goodOmens.setIsbn("9780575048003");
        goodOmens.setBookStatus(BookStatus.AVAILABLE);

        books.add(elantris);
        books.add(misery);
        books.add(bloodOfElves);
        books.add(timeOfContempt);
        books.add(poirotInvestigates);
        books.add(goodOmens);
        bookRepository.saveAll(books);

        // add users
        User janKowalski = new User();
        janKowalski.setId(1L);
        janKowalski.setFirstName("Jan");
        janKowalski.setLastName("Kowalski");
        janKowalski.setIdNumber("88021574862");
        janKowalski.setCountry("Poland");
        janKowalski.setState("mazowieckie");
        janKowalski.setCity("Warszawa");
        janKowalski.setStreet("Szkolna");
        janKowalski.setHomeNumber("7");
        janKowalski.addBook(misery);

        User tomaszNowak = new User();
        tomaszNowak.setId(2L);
        tomaszNowak.setFirstName("Tomasz");
        tomaszNowak.setLastName("Nowak");
        tomaszNowak.setIdNumber("74012285943");
        tomaszNowak.setCountry("Poland");
        tomaszNowak.setState("śląskie");
        tomaszNowak.setCity("Katowice");
        tomaszNowak.setStreet("Rynkowa");
        tomaszNowak.setHomeNumber("12");
        tomaszNowak.addBook(timeOfContempt);
        tomaszNowak.addBook(poirotInvestigates);

        users.add(janKowalski);
        users.add(tomaszNowak);
        userRepository.saveAll(users);
    }
}
