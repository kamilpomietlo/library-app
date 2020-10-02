package com.kamilpomietlo.libraryapp.repositories;

import com.kamilpomietlo.libraryapp.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    //Optional<User> findByIdNumber(String idNumber);
}
