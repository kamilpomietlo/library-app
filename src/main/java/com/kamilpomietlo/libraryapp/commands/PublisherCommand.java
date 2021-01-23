package com.kamilpomietlo.libraryapp.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PublisherCommand extends BaseEntityCommand {

    @NotBlank
    @Length(max = 128)
    private String name;

    private List<BookCommand> books = new ArrayList<>();
}
