package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.model.User;
import com.kamilpomietlo.libraryapp.model.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * {@inheritDoc}
 */
public interface MyUserDetailsService extends UserDetailsService {

    /**
     * Gets specific user role authorities.
     *
     * @param userRole user role
     * @return list of authorities
     */
    List<GrantedAuthority> getUserAuthority(UserRole userRole);

    /**
     * Builds user with authentications.
     *
     * @param user user object
     * @param authorities list of authorities
     * @return user details
     */
    UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities);

    /**
     * Gets id of logged in user.
     *
     * @return id value
     */
    Long getLoggedAccountId();
}
