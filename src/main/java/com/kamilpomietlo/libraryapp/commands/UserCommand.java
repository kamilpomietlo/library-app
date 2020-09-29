package com.kamilpomietlo.libraryapp.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserCommand extends BaseEntityCommand {

    private String firstName;
    private String lastName;
    private String idNumber;
    private String country;
    private String state;
    private String city;
    private String street;
    private String homeNumber;
    private Set<BookCommand> books = new HashSet<>();
}
