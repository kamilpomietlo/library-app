package com.kamilpomietlo.libraryapp.commands;

import com.kamilpomietlo.libraryapp.model.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class GenreCommand extends BaseEntityCommand {

    private String description;
    private Set<Book> books = new HashSet<>();
}
