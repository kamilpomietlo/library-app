package com.kamilpomietlo.libraryapp.model;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public enum Genre {
    FANTASY("genre.fantasy"),
    HISTORY("genre.history"),
    HORROR("genre.horror"),
    MYSTERY("genre.mystery"),
    ROMANCE("genre.romance"),
    SCIENCE_FICTION("genre.scienceFiction"),
    THRILLER("genre.thriller");

    private String description;
    private MessageSource messageSource;

    Genre(String description) {
        this.description = description;
    }

    public String getDisplayName() {
        return messageSource.getMessage(description, null, description, LocaleContextHolder.getLocale());
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
