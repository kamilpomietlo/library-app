package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.exceptions.NotFoundException;
import com.kamilpomietlo.libraryapp.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public abstract class BaseServiceImpl<T extends BaseEntity, R extends JpaRepository<T, Long>> implements BaseService<T> {

    protected R repository;

    public BaseServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public Set<T> findAll() {
        return new HashSet<>(repository.findAll());
    }

    @Override
    public T findById(Long id) {
        Optional<T> tOptional = repository.findById(id);

        if (tOptional.isEmpty()) {
            throw new NotFoundException("Object not found");
        }

        return tOptional.get();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
