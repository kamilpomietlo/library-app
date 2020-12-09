package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.PublisherCommand;
import com.kamilpomietlo.libraryapp.model.Publisher;

public interface PublisherService extends BaseService<Publisher> {

    PublisherCommand savePublisherCommand(PublisherCommand publisherCommand);
    PublisherCommand findCommandById(Long id);
}
