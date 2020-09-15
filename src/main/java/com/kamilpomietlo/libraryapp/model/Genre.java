package com.kamilpomietlo.libraryapp.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Genre extends BaseEntity {

    private String description;

    @OneToMany(mappedBy = "genre")
    private Set<Book> books = new HashSet<>();
}