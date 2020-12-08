package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.PublisherCommand;
import com.kamilpomietlo.libraryapp.converters.PublisherCommandToPublisher;
import com.kamilpomietlo.libraryapp.converters.PublisherToPublisherCommand;
import com.kamilpomietlo.libraryapp.exceptions.NotFoundException;
import com.kamilpomietlo.libraryapp.model.Publisher;
import com.kamilpomietlo.libraryapp.repositories.PublisherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class PublisherServiceImpl extends BaseServiceImpl<Publisher, PublisherRepository> implements PublisherService {

    private final PublisherCommandToPublisher publisherCommandToPublisher;
    private final PublisherToPublisherCommand publisherToPublisherCommand;
    private final String EXCEPTION_STRING = "Expected object not found.";

    public PublisherServiceImpl(PublisherRepository repository,
                                PublisherCommandToPublisher publisherCommandToPublisher,
                                PublisherToPublisherCommand publisherToPublisherCommand) {
        super(repository);
        this.publisherCommandToPublisher = publisherCommandToPublisher;
        this.publisherToPublisherCommand = publisherToPublisherCommand;
    }

    @Override
    public Set<Publisher> findByName(String name) {
        Set<Publisher> publisherSet = new HashSet<>();
        Optional<Publisher> publisherOptional = repository.findByNameIgnoreCaseContaining(name.trim());
        if (publisherOptional.isEmpty()) {
            throw new NotFoundException(EXCEPTION_STRING);
        } else {
            publisherSet.add(publisherOptional.get());
        }

        return publisherSet;
    }

    @Override
    public PublisherCommand savePublisherCommand(PublisherCommand publisherCommand) {
        Publisher detachedPublisher = publisherCommandToPublisher.convert(publisherCommand);
        Publisher savedPublisher = repository.save(detachedPublisher);

        return publisherToPublisherCommand.convert(savedPublisher);
    }

    @Override
    public PublisherCommand findCommandById(Long id) {
        return publisherToPublisherCommand.convert(findById(id));
    }
}
