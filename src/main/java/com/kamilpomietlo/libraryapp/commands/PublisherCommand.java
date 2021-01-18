package com.kamilpomietlo.libraryapp.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PublisherCommand extends BaseEntityCommand {

    @NotBlank
    @Length(max = 128)
    private String name;

    private Set<BookCommand> books = new HashSet<>();
}
