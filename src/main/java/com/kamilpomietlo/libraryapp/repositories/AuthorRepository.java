package com.kamilpomietlo.libraryapp.repositories;

import com.kamilpomietlo.libraryapp.model.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
    Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);
}
