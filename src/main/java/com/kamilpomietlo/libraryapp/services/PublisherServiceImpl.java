package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.Publisher;
import com.kamilpomietlo.libraryapp.repositories.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;
    private final String EXCEPTION_STRING = "Expected object not found.";

    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public Set<Publisher> getPublishers() {
        Set<Publisher> publisherSet = new HashSet<>();
        publisherRepository.findAll().iterator().forEachRemaining(publisherSet::add);

        return publisherSet;
    }

    public Publisher findByName(String name) {
        Optional<Publisher> publisherOptional = publisherRepository.findByName(name);
        if (publisherOptional.isEmpty()) {
            throw new RuntimeException(EXCEPTION_STRING);
        }

        return publisherOptional.get();
    }
}
