package com.kamilpomietlo.libraryapp.commands;

import com.kamilpomietlo.libraryapp.model.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BookCommand extends BaseEntityCommand {

    private String title;
    private List<Author> authors = new ArrayList<>();
    private Genre genre;
    private Publisher publisher;
    private CoverType coverType;
    private Integer yearOfRelease;
    private String isbn;
    private User user;
    private BookStatus bookStatus;
}
