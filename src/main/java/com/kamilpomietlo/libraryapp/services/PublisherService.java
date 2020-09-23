package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.Publisher;

import java.util.List;
import java.util.Set;

public interface PublisherService {

    Set<Publisher> getPublishers();
    List<Publisher> findByName(String name);
}
