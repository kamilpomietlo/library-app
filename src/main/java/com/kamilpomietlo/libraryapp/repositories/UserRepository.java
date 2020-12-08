package com.kamilpomietlo.libraryapp.repositories;

import com.kamilpomietlo.libraryapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
