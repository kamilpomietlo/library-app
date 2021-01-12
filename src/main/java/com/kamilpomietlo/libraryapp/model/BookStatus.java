package com.kamilpomietlo.libraryapp.model;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public enum BookStatus {
    AVAILABLE("bookStatus.available"),
    RESERVED("bookStatus.reserved"),
    BORROWED("bookStatus.borrowed");

    private String description;
    private MessageSource messageSource;

    BookStatus(String description) {
        this.description = description;
    }

    public String getDisplayName() {
        return messageSource.getMessage(description, null, description, LocaleContextHolder.getLocale());
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
