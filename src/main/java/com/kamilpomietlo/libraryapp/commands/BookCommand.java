package com.kamilpomietlo.libraryapp.commands;

import com.kamilpomietlo.libraryapp.model.Author;
import com.kamilpomietlo.libraryapp.model.BookStatus;
import com.kamilpomietlo.libraryapp.model.CoverType;
import com.kamilpomietlo.libraryapp.model.Genre;
import com.kamilpomietlo.libraryapp.validations.MaxCurrentYear;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class BookCommand extends BaseEntityCommand {

    @NotBlank
    @Length(max = 128)
    private String title;

    @Size(min = 2, max = 3)
    private Set<Author> authors = new HashSet<>();

    private Genre genre;
    private Long publisherId;
    private CoverType coverType;

    @NotNull
    @MaxCurrentYear(message = "{maxCurrentYear}")
    private Integer yearOfRelease;

    @ISBN
    private String isbn;

    private Long userId;
    private BookStatus bookStatus;
    private LocalDate dateOfReserveOrBorrow;
    private LocalDate deadlineDate;

    public String dateToString() {
        if (dateOfReserveOrBorrow != null) {
            return dateOfReserveOrBorrow.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }

        return null;
    }

    public String deadlineToString() {
        if (deadlineDate != null) {
            return deadlineDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }

        return null;
    }
}
