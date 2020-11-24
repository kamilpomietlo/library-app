package com.kamilpomietlo.libraryapp.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserCommand extends BaseEntityCommand {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z']*$")
    private String firstName;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z']*$")
    private String lastName;

    @NotBlank
    @PESEL
    private String idNumber;

    @NotBlank
    private String country;

    @NotBlank
    private String state;

    @NotBlank
    private String city;

    @NotBlank
    private String street;

    @NotBlank
    private String homeNumber;

    private Set<BookCommand> books = new HashSet<>();
}
