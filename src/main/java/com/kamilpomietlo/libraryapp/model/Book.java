package com.kamilpomietlo.libraryapp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Book extends BaseEntity {

    private String title;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @Enumerated(value = EnumType.STRING)
    private CoverType coverType;

    private Integer yearOfRelease;
    private String isbn;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(value = EnumType.STRING)
    private BookStatus bookStatus;

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
}
