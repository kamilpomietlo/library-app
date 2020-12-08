package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.commands.PublisherCommand;
import com.kamilpomietlo.libraryapp.model.Publisher;
import com.kamilpomietlo.libraryapp.services.PublisherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping("publisher/list")
    public String getPublishers(Model model) {
        model.addAttribute("publishers", publisherService.findAll());

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
    public String addNewPublisherSubmit(@Valid @ModelAttribute("publishers") PublisherCommand publisherCommand,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            return "publisher/add";
        }

        publisherService.savePublisherCommand(publisherCommand);

        return "redirect:/publisher/list";
    }

    @GetMapping("publisher/{id}/edit")
    public String editPublisherForm(@PathVariable String id, Model model) {
        model.addAttribute("publishers", publisherService.findCommandById(Long.valueOf(id)));

        return "publisher/edit";
    }

    @PostMapping("publisher/{id}/edit")
    public String editPublisherSubmit(@Valid @ModelAttribute("publishers") PublisherCommand publisherCommand,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            return "publisher/edit";
        }

        publisherService.savePublisherCommand(publisherCommand);

        return "redirect:/publisher/list";
    }
}
