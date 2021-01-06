package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.User;
import com.kamilpomietlo.libraryapp.model.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MyUserDetailsServiceImpl implements MyUserDetailsService {

    private final UserService userService;

    public MyUserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findUserByEmail(email);
        List<GrantedAuthority> authorities = getUserAuthority(user.getUserRole());

        return buildUserForAuthentication(user, authorities);
    }

    @Override
    public List<GrantedAuthority> getUserAuthority(UserRole userRole) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(userRole.toString()));

        return grantedAuthorities;
    }

    @Override
    public UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getEnabled(), true, true, !user.getLocked(), authorities);
    }

    @Override
    public Long getLoggedAccountId() {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = user.getUsername();

        return userService.findUserByEmail(email).getId();
    }
}
