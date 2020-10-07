package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.PublisherCommand;
import com.kamilpomietlo.libraryapp.converters.PublisherCommandToPublisher;
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
    private final String EXCEPTION_STRING = "Expected object not found.";

    public PublisherServiceImpl(PublisherRepository publisherRepository,
                                PublisherCommandToPublisher publisherCommandToPublisher) {
        this.publisherRepository = publisherRepository;
        this.publisherCommandToPublisher = publisherCommandToPublisher;
    }

    @Override
    public Set<Publisher> getPublishers() {
        Set<Publisher> publisherSet = new HashSet<>();
        publisherRepository.findAll().iterator().forEachRemaining(publisherSet::add);

        return publisherSet;
    }

    public Set<Publisher> findByName(String name) {
        Set<Publisher> publisherSet = new HashSet<>();
        Optional<Publisher> publisherOptional = publisherRepository.findByName(name);
        if (publisherOptional.isEmpty()) {
            throw new RuntimeException(EXCEPTION_STRING);
        } else {
            publisherSet.add(publisherOptional.get());
        }

        return publisherSet;
    }

    @Override
    @Transactional
    public void savePublisherCommand(PublisherCommand publisherCommand) {
        Publisher detachedPublisher = publisherCommandToPublisher.convert(publisherCommand);

        if (detachedPublisher != null) {
            publisherRepository.save(detachedPublisher);
        }
    }
}
