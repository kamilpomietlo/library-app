package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.PublisherCommand;
import com.kamilpomietlo.libraryapp.converters.PublisherCommandToPublisher;
import com.kamilpomietlo.libraryapp.converters.PublisherToPublisherCommand;
import com.kamilpomietlo.libraryapp.model.Publisher;
import com.kamilpomietlo.libraryapp.repositories.PublisherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PublisherServiceImpl extends BaseServiceImpl<Publisher, PublisherRepository> implements PublisherService {

    private final PublisherCommandToPublisher publisherCommandToPublisher;
    private final PublisherToPublisherCommand publisherToPublisherCommand;

    public PublisherServiceImpl(PublisherRepository repository,
                                PublisherCommandToPublisher publisherCommandToPublisher,
                                PublisherToPublisherCommand publisherToPublisherCommand) {
        super(repository);
        this.publisherCommandToPublisher = publisherCommandToPublisher;
        this.publisherToPublisherCommand = publisherToPublisherCommand;
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
