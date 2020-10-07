package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.commands.PublisherCommand;
import com.kamilpomietlo.libraryapp.model.Publisher;
import com.kamilpomietlo.libraryapp.services.PublisherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping("publisher/list")
    public String getPublishers(Model model) {
        model.addAttribute("publishers", publisherService.getPublishers());

        return "publisher/list";
    }

    @GetMapping("publisher/find")
    public String publisherSearchForm(Model model) {
        model.addAttribute("publishers", new Publisher());

        return "publisher/find";
    }

    @PostMapping("publisher/find")
    public String publisherSearchSubmit(@ModelAttribute Publisher publisher, Model model) {
        model.addAttribute("publishers", publisherService.findByName(publisher.getName()));

        return "publisher/list";
    }

    @GetMapping("publisher/add")
    public String addNewPublisherForm(Model model) {
        model.addAttribute("publishers", new PublisherCommand());

        return "publisher/add";
    }

    @PostMapping("publisher/add")
    public String addNewPublisherSubmit(@ModelAttribute PublisherCommand publisherCommand) {
        publisherService.savePublisherCommand(publisherCommand);

        return "redirect:/publisher/list";
    }
}
