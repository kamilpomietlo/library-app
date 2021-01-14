package com.kamilpomietlo.libraryapp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class ConfirmationToken extends BaseEntity {

    private String confirmationToken;
    private LocalDate createDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ConfirmationToken() {
    }

    public ConfirmationToken(User user) {
        this.user = user;
        this.confirmationToken = UUID.randomUUID().toString();
        this.createDate = LocalDate.now();
    }
}
