package com.kamilpomietlo.libraryapp.bootstrap;

import com.kamilpomietlo.libraryapp.model.*;
import com.kamilpomietlo.libraryapp.repositories.AuthorRepository;
import com.kamilpomietlo.libraryapp.repositories.BookRepository;
import com.kamilpomietlo.libraryapp.repositories.PublisherRepository;
import com.kamilpomietlo.libraryapp.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Profile("default")
@Component
public class LibraryBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public LibraryBootstrap(AuthorRepository authorRepository, PublisherRepository publisherRepository,
                            BookRepository bookRepository, UserRepository userRepository) {
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
        elantris.setGenre(Genre.FANTASY);
        elantris.addPublisher(torBooks);
        elantris.setCoverType(CoverType.SOFT);
        elantris.setYearOfRelease(2005);
        elantris.setIsbn("9788374806671");
        elantris.setBookStatus(BookStatus.AVAILABLE);

        Book misery = new Book();
        misery.setId(2L);
        misery.setTitle("Misery");
        misery.addAuthor(stephenKing);
        misery.setGenre(Genre.HORROR);
        misery.addPublisher(vikingPress);
        misery.setCoverType(CoverType.SOFT);
        misery.setYearOfRelease(1987);
        misery.setIsbn("9780670813643");
        misery.setBookStatus(BookStatus.RESERVED);
        misery.setDateOfReserveOrBorrow(LocalDate.now().minusDays(1));
        misery.setDeadlineDate(LocalDate.now().plusDays(6));

        Book bloodOfElves = new Book();
        bloodOfElves.setId(3L);
        bloodOfElves.setTitle("Blood of Elves");
        bloodOfElves.addAuthor(andrzejSapkowski);
        bloodOfElves.setGenre(Genre.FANTASY);
        bloodOfElves.addPublisher(superNowa);
        bloodOfElves.setCoverType(CoverType.SOFT);
        bloodOfElves.setYearOfRelease(1994);
        bloodOfElves.setIsbn("9788375780659");
        bloodOfElves.setBookStatus(BookStatus.AVAILABLE);

        Book timeOfContempt = new Book();
        timeOfContempt.setId(4L);
        timeOfContempt.setTitle("Time of Contempt");
        timeOfContempt.addAuthor(andrzejSapkowski);
        timeOfContempt.setGenre(Genre.FANTASY);
        timeOfContempt.addPublisher(superNowa);
        timeOfContempt.setCoverType(CoverType.SOFT);
        timeOfContempt.setYearOfRelease(1995);
        timeOfContempt.setIsbn("9788375780666");
        timeOfContempt.setBookStatus(BookStatus.BORROWED);
        timeOfContempt.setDateOfReserveOrBorrow(LocalDate.now().minusDays(3));
        timeOfContempt.setDeadlineDate(LocalDate.now().plusDays(27));

        Book poirotInvestigates = new Book();
        poirotInvestigates.setId(5L);
        poirotInvestigates.setTitle("Poirot Investigates");
        poirotInvestigates.addAuthor(agathaChristie);
        poirotInvestigates.setGenre(Genre.THRILLER);
        poirotInvestigates.addPublisher(bodleyHead);
        poirotInvestigates.setCoverType(CoverType.HARD);
        poirotInvestigates.setYearOfRelease(1924);
        poirotInvestigates.setIsbn("9788577991273");
        poirotInvestigates.setBookStatus(BookStatus.AVAILABLE);

        Book goodOmens = new Book();
        goodOmens.setId(6L);
        goodOmens.setTitle("Good Omens");
        goodOmens.addAuthor(terryPratchett);
        goodOmens.addAuthor(neilGaiman);
        goodOmens.setGenre(Genre.SCIENCE_FICTION);
        goodOmens.addPublisher(gollancz);
        goodOmens.setCoverType(CoverType.HARD);
        goodOmens.setYearOfRelease(1990);
        goodOmens.setIsbn("9780575048003");
        goodOmens.setBookStatus(BookStatus.AVAILABLE);

        Book misery2 = new Book();
        misery2.setId(7L);
        misery2.setTitle("Misery");
        misery2.addAuthor(stephenKing);
        misery2.setGenre(Genre.ROMANCE);
        misery2.addPublisher(vikingPress);
        misery2.setCoverType(CoverType.SOFT);
        misery2.setYearOfRelease(1987);
        misery2.setIsbn("9780670813643");
        misery2.setBookStatus(BookStatus.AVAILABLE);

        Book bloodOfElves2 = new Book();
        bloodOfElves2.setId(8L);
        bloodOfElves2.setTitle("Blood of Elves");
        bloodOfElves2.addAuthor(andrzejSapkowski);
        bloodOfElves2.setGenre(Genre.HISTORY);
        bloodOfElves2.addPublisher(superNowa);
        bloodOfElves2.setCoverType(CoverType.HARD);
        bloodOfElves2.setYearOfRelease(1994);
        bloodOfElves2.setIsbn("9788375780659");
        bloodOfElves2.setBookStatus(BookStatus.AVAILABLE);

        Book goodOmens2 = new Book();
        goodOmens2.setId(9L);
        goodOmens2.setTitle("Good Omens");
        goodOmens2.addAuthor(terryPratchett);
        goodOmens2.addAuthor(neilGaiman);
        goodOmens2.setGenre(Genre.MYSTERY);
        goodOmens2.addPublisher(gollancz);
        goodOmens2.setCoverType(CoverType.SOFT);
        goodOmens2.setYearOfRelease(1990);
        goodOmens2.setIsbn("9780575048003");
        goodOmens2.setBookStatus(BookStatus.AVAILABLE);

        books.add(elantris);
        books.add(misery);
        books.add(bloodOfElves);
        books.add(timeOfContempt);
        books.add(poirotInvestigates);
        books.add(goodOmens);
        books.add(misery2);
        books.add(bloodOfElves2);
        books.add(goodOmens2);
        bookRepository.saveAll(books);

        // add users
        User janKowalski = new User();
        janKowalski.setId(1L);
        janKowalski.setFirstName("Jan");
        janKowalski.setLastName("Kowalski");
        janKowalski.setIdNumber("88021574863");
        janKowalski.setCountry("Polska");
        janKowalski.setState("mazowieckie");
        janKowalski.setCity("Warszawa");
        janKowalski.setStreet("Szkolna");
        janKowalski.setHomeNumber("7");
        janKowalski.addBook(misery);
        janKowalski.addBook(timeOfContempt);
        janKowalski.setEmail("user@example.pl");
        janKowalski.setPassword(passwordEncoder().encode("123"));
        janKowalski.setUserRole(UserRole.USER);
        janKowalski.setLocked(false);
        janKowalski.setEnabled(true);

        User tomaszNowak = new User();
        tomaszNowak.setId(2L);
        tomaszNowak.setFirstName("Tomasz");
        tomaszNowak.setLastName("Nowak");
        tomaszNowak.setIdNumber("74012285959");
        tomaszNowak.setCountry("Polska");
        tomaszNowak.setState("śląskie");
        tomaszNowak.setCity("Katowice");
        tomaszNowak.setStreet("Rynkowa");
        tomaszNowak.setHomeNumber("12");
        tomaszNowak.setEmail("librarian@example.pl");
        tomaszNowak.setPassword(passwordEncoder().encode("456"));
        tomaszNowak.setUserRole(UserRole.LIBRARIAN);
        tomaszNowak.setLocked(false);
        tomaszNowak.setEnabled(true);

        User krzysztofNowakowski = new User();
        krzysztofNowakowski.setId(3L);
        krzysztofNowakowski.setFirstName("Krzysztof");
        krzysztofNowakowski.setLastName("Nowakowski");
        krzysztofNowakowski.setIdNumber("95071375498");
        krzysztofNowakowski.setCountry("Polska");
        krzysztofNowakowski.setState("opolskie");
        krzysztofNowakowski.setCity("Opole");
        krzysztofNowakowski.setStreet("Zamkowa");
        krzysztofNowakowski.setHomeNumber("77");
        krzysztofNowakowski.setEmail("admin@example.pl");
        krzysztofNowakowski.setPassword(passwordEncoder().encode("789"));
        krzysztofNowakowski.setUserRole(UserRole.ADMIN);
        krzysztofNowakowski.setLocked(false);
        krzysztofNowakowski.setEnabled(true);

        users.add(janKowalski);
        users.add(tomaszNowak);
        users.add(krzysztofNowakowski);
        userRepository.saveAll(users);
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
