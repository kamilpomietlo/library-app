package com.kamilpomietlo.libraryapp.repositories;

import com.kamilpomietlo.libraryapp.model.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {

}
