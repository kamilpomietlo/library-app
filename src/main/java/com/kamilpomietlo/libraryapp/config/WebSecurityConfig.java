package com.kamilpomietlo.libraryapp.config;

import com.kamilpomietlo.libraryapp.services.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Class used for web security configuration.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MyUserDetailsService myUserDetailsService;

    public WebSecurityConfig(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] anonymousMatchers = new String[] {
                "/login", "/register", "/register/confirm"
        };

        String[] userMatchers = new String[] {
                "/book/{\\d+}/reserve"
        };

        String[] librarianMatchers = new String[] {
                "/book/list", "/book/add", "/book/{\\d+}/edit", "/book/{\\d+}/delete", "/book/reservations",
                "/book/{\\d+}/accept-reservation", "/book/borrowed", "/book/{\\d+}/accept-returning",
                "/author/list", "/author/add", "/author/{\\d+}/edit", "/author/{\\d+}/show-books",
                "/publisher/list", "/publisher/add", "/publisher/{\\d+}/edit", "/publisher/{\\d+}/show-books"
        };

        http
                .authorizeRequests()
                .antMatchers("/", "/index").permitAll()
                .antMatchers("/logout").authenticated()
                .antMatchers(anonymousMatchers).anonymous()
                .antMatchers("/user/edit", "/user/account").hasAnyAuthority("USER", "LIBRARIAN", "ADMIN")
                .antMatchers(userMatchers).hasAnyAuthority("USER", "ADMIN")
                .antMatchers(librarianMatchers).hasAnyAuthority("LIBRARIAN", "ADMIN")
                .antMatchers("/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()

                .exceptionHandling().accessDeniedPage("/403")
                .and()

                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .and()

                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring().antMatchers("/h2-console/**", "/css/**", "/images/**");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(myUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * Provides {@code BCryptPasswordEncoder}.
     *
     * @return new {@code BCryptPasswordEncoder} object
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
