package com.kamilpomietlo.libraryapp.repositories;

import com.kamilpomietlo.libraryapp.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Set<Author> findByNameIgnoreCaseContaining(String name);
}
