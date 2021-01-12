package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.BookStatus;
import com.kamilpomietlo.libraryapp.model.CoverType;
import com.kamilpomietlo.libraryapp.model.Genre;
import com.kamilpomietlo.libraryapp.model.UserRole;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.EnumSet;

@Service
public class EnumService {

    private MessageSource messageSource;

    public EnumService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @PostConstruct
    public void postConstruct() {
        for (BookStatus bookStatus : EnumSet.allOf(BookStatus.class)) {
            bookStatus.setMessageSource(messageSource);
        }

        for (CoverType coverType : EnumSet.allOf(CoverType.class)) {
            coverType.setMessageSource(messageSource);
        }

        for (Genre genre : EnumSet.allOf(Genre.class)) {
            genre.setMessageSource(messageSource);
        }

        for (UserRole userRole : EnumSet.allOf(UserRole.class)) {
            userRole.setMessageSource(messageSource);
        }
    }
}
