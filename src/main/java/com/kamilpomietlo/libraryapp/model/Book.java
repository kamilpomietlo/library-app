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

    // cascade causes problem?
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "books")
    private List<Author> authors = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @Enumerated(value = EnumType.STRING)
    private CoverType coverType;

    private Integer yearOfRelease;
    private String isbn;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

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
