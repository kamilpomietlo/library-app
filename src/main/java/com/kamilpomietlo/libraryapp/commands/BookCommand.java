package com.kamilpomietlo.libraryapp.commands;

import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.model.BookStatus;
import com.kamilpomietlo.libraryapp.model.CoverType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.ISBN;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class BookCommand extends BaseEntityCommand {

    @NotBlank
    private String title;

    @Size(min = 2, max = 3)
    private Set<Author> authors = new HashSet<>();

    private Long genreId;
    private Long publisherId;
    private CoverType coverType;

    @NotNull
    @Max(2020)
    private Integer yearOfRelease;

    @ISBN
    private String isbn;

    private Long userId;
    private BookStatus bookStatus;
}
