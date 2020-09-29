package com.kamilpomietlo.libraryapp.commands;

import com.kamilpomietlo.libraryapp.model.BookStatus;
import com.kamilpomietlo.libraryapp.model.CoverType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookCommand extends BaseEntityCommand {

    private String title;
    private Long genreId;
    private Long publisherId;
    private CoverType coverType;
    private Integer yearOfRelease;
    private String isbn;
    private Long userId;
    private BookStatus bookStatus;
}
