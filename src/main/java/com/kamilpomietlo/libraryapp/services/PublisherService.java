package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.Publisher;

import java.util.Set;

public interface PublisherService {

    Set<Publisher> getPublishers();
    Publisher findByName(String name);
}
