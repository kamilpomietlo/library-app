package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.BaseEntity;

import java.util.List;

public interface BaseService<T extends BaseEntity> {

    List<T> findAll();
    T findById(Long id);
    void deleteById(Long id);
}
