package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.PublisherCommand;
import com.kamilpomietlo.libraryapp.model.Publisher;

import java.util.Set;

public interface PublisherService {

    Set<Publisher> getPublishers();
    Set<Publisher> findByName(String name);
    void savePublisherCommand(PublisherCommand publisherCommand);
}
