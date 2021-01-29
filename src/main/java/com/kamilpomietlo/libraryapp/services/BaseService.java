package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.exceptions.NotFoundException;
import com.kamilpomietlo.libraryapp.model.BaseEntity;

import java.util.List;

/**
 * Provides basic and the most common operations performed on repositories.
 *
 * @param <T> the type of object
 */
public interface BaseService<T extends BaseEntity> {

    /**
     * Gets all objects of given type.
     *
     * @return list of objects
     */
    List<T> findAll();

    /**
     * Finds the object by provided id. Exception is thrown if not found.
     *
     * @param id object id
     * @return object of given type
     * @throws NotFoundException exception
     */
    T findById(Long id) throws NotFoundException;

    /**
     * Deletes object of given id from repository.
     *
     * @param id object id
     */
    void deleteById(Long id);
}
