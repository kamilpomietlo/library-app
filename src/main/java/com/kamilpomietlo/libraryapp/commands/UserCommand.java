package com.kamilpomietlo.libraryapp.commands;

import com.kamilpomietlo.libraryapp.model.UserRole;
import com.kamilpomietlo.libraryapp.validations.EditInfo;
import com.kamilpomietlo.libraryapp.validations.EmailNotInUse;
import com.kamilpomietlo.libraryapp.validations.PasswordMatches;
import com.kamilpomietlo.libraryapp.validations.RegisterInfo;
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
import java.util.ArrayList;
import java.util.List;

@PasswordMatches(groups = RegisterInfo.class, message = "{password.matches}")
@Getter
@Setter
@NoArgsConstructor
public class UserCommand extends BaseEntityCommand {

    @NotBlank(groups = {RegisterInfo.class, EditInfo.class})
    @Pattern(groups = {RegisterInfo.class, EditInfo.class}, regexp = "^[\\p{L}]*$")
    @Length(groups = {RegisterInfo.class, EditInfo.class}, max = 128)
    private String firstName;

    @NotBlank(groups = {RegisterInfo.class, EditInfo.class})
    @Pattern(groups = {RegisterInfo.class, EditInfo.class}, regexp = "^[\\p{L}]*[-]?[\\p{L}]+$")
    @Length(groups = {RegisterInfo.class, EditInfo.class}, max = 128)
    private String lastName;

    @NotBlank(groups = {RegisterInfo.class, EditInfo.class})
    @PESEL(groups = {RegisterInfo.class, EditInfo.class})
    private String idNumber;

    @NotBlank(groups = {RegisterInfo.class, EditInfo.class})
    @Length(groups = {RegisterInfo.class, EditInfo.class}, max = 128)
    private String country;

    @NotBlank(groups = {RegisterInfo.class, EditInfo.class})
    @Length(groups = {RegisterInfo.class, EditInfo.class}, max = 128)
    private String state;

    @NotBlank(groups = {RegisterInfo.class, EditInfo.class})
    @Length(groups = {RegisterInfo.class, EditInfo.class}, max = 128)
    private String city;

    @NotBlank(groups = {RegisterInfo.class, EditInfo.class})
    @Length(groups = {RegisterInfo.class, EditInfo.class}, max = 128)
    private String street;

    @NotBlank(groups = {RegisterInfo.class, EditInfo.class})
    @Pattern(groups = {RegisterInfo.class, EditInfo.class}, regexp = "^[0-9]*[A-Za-z]?[/]?[0-9]*$")
    @Length(groups = {RegisterInfo.class, EditInfo.class}, max = 128)
    private String homeNumber;

    private List<BookCommand> books = new ArrayList<>();

    @NotBlank(groups = RegisterInfo.class)
    @Email(groups = RegisterInfo.class)
    @EmailNotInUse(groups = RegisterInfo.class, message = "{email.notInUse}")
    private String email;

    @NotBlank(groups = RegisterInfo.class)
    @Length(groups = RegisterInfo.class, min = 3)
    private String password;

    @NotBlank(groups = RegisterInfo.class)
    private String matchingPassword;

    @Enumerated(value = EnumType.STRING)
    private UserRole userRole = UserRole.USER;

    private Boolean locked = false;
    private Boolean enabled = false;

    public String toString() {
        return firstName + " " + lastName;
    }
}
