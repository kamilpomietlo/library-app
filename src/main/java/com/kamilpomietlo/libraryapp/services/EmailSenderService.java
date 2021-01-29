package com.kamilpomietlo.libraryapp.services;

import org.springframework.mail.SimpleMailMessage;

/**
 * Provides operations related to sending e-mails.
 */
public interface EmailSenderService {

    /**
     * Sends e-mail.
     *
     * @param email e-mail object
     */
    void sendEmail(SimpleMailMessage email);
}
