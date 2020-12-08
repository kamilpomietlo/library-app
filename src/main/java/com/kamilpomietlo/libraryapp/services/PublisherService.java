package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.PublisherCommand;
import com.kamilpomietlo.libraryapp.model.Publisher;

import java.util.Set;

public interface PublisherService extends BaseService<Publisher> {

    Set<Publisher> findByName(String name);
    PublisherCommand savePublisherCommand(PublisherCommand publisherCommand);
    PublisherCommand findCommandById(Long id);
}
