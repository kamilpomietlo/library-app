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
public class Genre extends BaseEntity {

    private String description;

    @OneToMany(mappedBy = "genre")
    private Set<Book> books = new HashSet<>();
}