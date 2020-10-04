package com.kamilpomietlo.libraryapp.commands;

import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.model.BookStatus;
import com.kamilpomietlo.libraryapp.model.CoverType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class BookCommand extends BaseEntityCommand {

    private String title;
    private Set<Author> authors = new HashSet<>();
    private Long genreId;
    private Long publisherId;
    private CoverType coverType;
    private Integer yearOfRelease;
    private String isbn;
    private Long userId;
    private BookStatus bookStatus;
}
