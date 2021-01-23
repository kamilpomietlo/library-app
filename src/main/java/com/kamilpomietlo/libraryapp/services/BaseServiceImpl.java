package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.exceptions.NotFoundException;
import com.kamilpomietlo.libraryapp.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BaseServiceImpl<T extends BaseEntity, R extends JpaRepository<T, Long>> implements BaseService<T> {

    protected R repository;

    public BaseServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(repository.findAll());
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
