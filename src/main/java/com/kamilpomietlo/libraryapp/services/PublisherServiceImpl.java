package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.PublisherCommand;
import com.kamilpomietlo.libraryapp.converters.PublisherCommandToPublisher;
import com.kamilpomietlo.libraryapp.converters.PublisherToPublisherCommand;
import com.kamilpomietlo.libraryapp.model.Publisher;
import com.kamilpomietlo.libraryapp.repositories.PublisherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;
    private final PublisherCommandToPublisher publisherCommandToPublisher;
    private final PublisherToPublisherCommand publisherToPublisherCommand;
    private final String EXCEPTION_STRING = "Expected object not found.";

    public PublisherServiceImpl(PublisherRepository publisherRepository,
                                PublisherCommandToPublisher publisherCommandToPublisher,
                                PublisherToPublisherCommand publisherToPublisherCommand) {
        this.publisherRepository = publisherRepository;
        this.publisherCommandToPublisher = publisherCommandToPublisher;
        this.publisherToPublisherCommand = publisherToPublisherCommand;
    }

    @Override
    public Set<Publisher> getPublishers() {
        Set<Publisher> publisherSet = new HashSet<>();
        publisherRepository.findAll().iterator().forEachRemaining(publisherSet::add);

        return publisherSet;
    }

    @Override
    public Set<Publisher> findByName(String name) {
        Set<Publisher> publisherSet = new HashSet<>();
        Optional<Publisher> publisherOptional = publisherRepository.findByNameIgnoreCaseContaining(name.trim());
        if (publisherOptional.isEmpty()) {
            throw new RuntimeException(EXCEPTION_STRING);
        } else {
            publisherSet.add(publisherOptional.get());
        }

        return publisherSet;
    }

    @Override
    @Transactional
    public PublisherCommand savePublisherCommand(PublisherCommand publisherCommand) {
        Publisher detachedPublisher = publisherCommandToPublisher.convert(publisherCommand);
        Publisher savedPublisher = publisherRepository.save(detachedPublisher);

        return publisherToPublisherCommand.convert(savedPublisher);
    }

    @Override
    public Publisher findById(Long id) {
        Optional<Publisher> publisherOptional = publisherRepository.findById(id);

        if (publisherOptional.isEmpty()) {
            throw new RuntimeException(EXCEPTION_STRING);
        }

        return publisherOptional.get();
    }

    @Override
    @Transactional
    public PublisherCommand findCommandById(Long id) {
        return publisherToPublisherCommand.convert(findById(id));
    }
}
