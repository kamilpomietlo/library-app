package com.kamilpomietlo.libraryapp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true, exclude = "books")
@Entity
public class User extends BaseEntity implements UserDetails {

    private String firstName;
    private String lastName;
    private String idNumber;
    private String country;
    private String state;
    private String city;
    private String street;
    private String homeNumber;

    @OneToMany
    @JoinColumn(name = "user_id")
    private Set<Book> books = new HashSet<>();

    private String email;
    private String password;

    @Enumerated(value = EnumType.STRING)
    private UserRole userRole = UserRole.USER;

    private Boolean locked = false;
    private Boolean enabled = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(userRole.name());

        return Collections.singletonList(simpleGrantedAuthority);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String toString() {
        return firstName + " " + lastName;
    }

    public void addBook(Book book) {
        if (book != null) {
            this.books.add(book);
            book.setUser(this);
        }
    }
}
