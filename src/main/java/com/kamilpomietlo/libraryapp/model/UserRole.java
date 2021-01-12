package com.kamilpomietlo.libraryapp.model;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public enum UserRole {
    ADMIN("userRole.admin"),
    LIBRARIAN("userRole.librarian"),
    USER("userRole.user");

    private String description;
    private MessageSource messageSource;

    UserRole(String description) {
        this.description = description;
    }

    public String getDisplayName() {
        return messageSource.getMessage(description, null, description, LocaleContextHolder.getLocale());
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
