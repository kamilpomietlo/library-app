package com.kamilpomietlo.libraryapp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"authors"})
@Entity
public class Book extends BaseEntity {

    private String title;

    @ManyToMany
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors = new HashSet<>();

    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @Enumerated(value = EnumType.STRING)
    private CoverType coverType;

    private Integer yearOfRelease;
    private String isbn;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Enumerated(value = EnumType.STRING)
    private BookStatus bookStatus = BookStatus.AVAILABLE;

    private LocalDate dateOfReserveOrBorrow;
    private LocalDate deadlineDate;
    private Long numberOfProlongs = 0L;

    public void addAuthor(Author author) {
        if (author != null) {
            this.authors.add(author);
            author.getBooks().add(this);
        }
    }

    public void addPublisher(Publisher publisher) {
        if (publisher != null) {
            this.publisher = publisher;
            publisher.getBooks().add(this);
        }
    }

    public String dateToString() {
        if (dateOfReserveOrBorrow != null) {
            return dateOfReserveOrBorrow.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }

        return null;
    }

    public String deadlineToString() {
        if (deadlineDate != null) {
            return deadlineDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }

        return null;
    }
}
