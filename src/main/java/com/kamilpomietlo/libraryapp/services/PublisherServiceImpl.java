package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.Publisher;
import com.kamilpomietlo.libraryapp.repositories.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public List<Publisher> findByName(String name) {
        List<Publisher> publisherList = new ArrayList<>();
        Optional<Publisher> publisherOptional = publisherRepository.findByName(name);
        if (publisherOptional.isEmpty()) {
            throw new RuntimeException(EXCEPTION_STRING);
        } else {
            publisherList.add(publisherOptional.get());
        }

        return publisherList;
    }
}
