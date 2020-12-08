package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.BaseEntity;

import java.util.Set;

public interface BaseService<T extends BaseEntity> {

    Set<T> findAll();
    T findById(Long id);
    T save(T object);
    void deleteById(Long id);
}
