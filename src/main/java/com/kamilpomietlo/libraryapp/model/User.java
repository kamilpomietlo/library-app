package com.kamilpomietlo.libraryapp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true, exclude = "books")
@Entity
public class User extends BaseEntity {

    private String firstName;
    private String lastName;
    private String idNumber;
    private String country;
    private String state;
    private String city;
    private String street;
    private String homeNumber;

    @OneToMany(mappedBy = "user")
    private Set<Book> books = new HashSet<>();

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    // todo: test
    public void addBook(Book book) {
        if (book != null) {
            this.books.add(book);
            book.setUser(this);
        }
    }
}

