package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.BaseEntity;

import java.util.Set;

public interface BaseService<T extends BaseEntity> {

    Set<T> findAll();
    T findById(Long id);
    void deleteById(Long id);
}
