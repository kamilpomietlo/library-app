package com.kamilpomietlo.libraryapp.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Publisher extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "publisher")
    private Set<Book> books = new HashSet<>();
}
