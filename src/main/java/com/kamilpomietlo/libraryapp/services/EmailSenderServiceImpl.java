package com.kamilpomietlo.libraryapp.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * {@inheritDoc}
 */
@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    private JavaMailSender javaMailSender;

    public EmailSenderServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendEmail(SimpleMailMessage mailMessage) {
        javaMailSender.send(mailMessage);
    }
}
