package com.kamilpomietlo.libraryapp.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class AuthorCommand extends BaseEntityCommand {

    private String firstName;
    private String lastName;
    private Set<BookCommand> books = new HashSet<>();
}
