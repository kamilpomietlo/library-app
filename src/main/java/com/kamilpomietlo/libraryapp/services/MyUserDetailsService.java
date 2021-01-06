package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.User;
import com.kamilpomietlo.libraryapp.model.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface MyUserDetailsService extends UserDetailsService {

    List<GrantedAuthority> getUserAuthority(UserRole userRole);
    UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities);
    Long getLoggedAccountId();
}
