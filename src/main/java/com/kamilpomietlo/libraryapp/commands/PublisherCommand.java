package com.kamilpomietlo.libraryapp.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PublisherCommand extends BaseEntityCommand {

    private String name;
    private Set<BookCommand> books = new HashSet<>();
}
