package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.commands.PublisherCommand;
import com.kamilpomietlo.libraryapp.services.PublisherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller related to {@code Publisher} object.
 */
@Slf4j
@RequestMapping("publisher")
@Controller
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping("list")
    public String getPublishers(Model model) {
        model.addAttribute("publishers", publisherService.findAll());

        return "publisher/list";
    }

    @GetMapping("add")
    public String addPublisherForm(Model model) {
        model.addAttribute("publishers", new PublisherCommand());

        return "publisher/add";
    }

    /**
     * Submits form data for adding new object and saves it if validation is successful.
     *
     * @param publisherCommand object to be added
     * @param bindingResult performs validation of the object
     * @return add form when validation failed, otherwise redirects to list of publishers
     */
    @PostMapping("add")
    public String addPublisherSubmit(@Valid @ModelAttribute("publishers") PublisherCommand publisherCommand,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            return "publisher/add";
        }

        publisherService.savePublisherCommand(publisherCommand);

        return "redirect:/publisher/list";
    }

    @GetMapping("{id}/edit")
    public String editPublisherForm(@PathVariable Long id, Model model) {
        model.addAttribute("publishers", publisherService.findCommandById(id));

        return "publisher/edit";
    }

    /**
     * Submits form data for editing existing object and saves it if validation is successful.
     *
     * @param publisherCommand object to be edited
     * @param bindingResult performs validation of the object
     * @return edit form when validation failed, otherwise redirects to list of publishers
     */
    @PostMapping("{id}/edit")
    public String editPublisherSubmit(@Valid @ModelAttribute("publishers") PublisherCommand publisherCommand,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            return "publisher/edit";
        }

        publisherService.savePublisherCommand(publisherCommand);

        return "redirect:/publisher/list";
    }

    @GetMapping("{id}/show-books")
    public String showBooks(@PathVariable Long id, Model model) {
        model.addAttribute("books", publisherService.getPublishersBooks(id));

        return "book/list";
    }
}
