package com.kamilpomietlo.libraryapp.commands;

import com.kamilpomietlo.libraryapp.model.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterCommand extends BaseEntityCommand {

    @NotBlank
    @Pattern(regexp = "^[\\p{L}]*$")
    private String firstName;

    @NotBlank
    @Pattern(regexp = "^[\\p{L}]*$")
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(min = 3)
    private String password;

    @Enumerated(value = EnumType.STRING)
    private UserRole userRole = UserRole.USER;

    private Boolean locked = false;
    private Boolean enabled = false;
}
