package com.kamilpomietlo.libraryapp.model;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public enum CoverType {
    SOFT("coverType.soft"),
    HARD("coverType.hard");

    private String description;
    private MessageSource messageSource;

    CoverType(String description) {
        this.description = description;
    }

    public String getDisplayName() {
        return messageSource.getMessage(description, null, description, LocaleContextHolder.getLocale());
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
