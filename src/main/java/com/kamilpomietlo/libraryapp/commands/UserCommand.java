package com.kamilpomietlo.libraryapp.commands;

import com.kamilpomietlo.libraryapp.model.UserRole;
import com.kamilpomietlo.libraryapp.validators.EmailNotInUse;
import com.kamilpomietlo.libraryapp.validators.PasswordMatches;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@PasswordMatches
@Getter
@Setter
@NoArgsConstructor
public class UserCommand extends BaseEntityCommand {

    @NotBlank
    @Pattern(regexp = "^[\\p{L}]*$")
    private String firstName;

    @NotBlank
    @Pattern(regexp = "^[\\p{L}]*$")
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

    @NotBlank
    @Email
    @EmailNotInUse
    private String email;

    @NotBlank
    @Length(min = 3)
    private String password;

    @NotBlank
    private String matchingPassword;

    @Enumerated(value = EnumType.STRING)
    private UserRole userRole = UserRole.USER;

    private Boolean locked = false;
    private Boolean enabled = false;
}
