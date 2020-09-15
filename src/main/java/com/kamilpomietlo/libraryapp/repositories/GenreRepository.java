package com.kamilpomietlo.libraryapp.repositories;

import com.kamilpomietlo.libraryapp.model.Genre;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<Genre, Long> {

}
