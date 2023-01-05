package com.banktest.account.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class WebSecurityConfig {

    private static final String[] WHITE_LIST_URLS = {"/register", "/h2/**", "/verifyRegistration", "/resendToken"};

    private static final String[] AUTHENTICATED_LIST_URLS = {"/transactions-type/**", "/transactions/**", "/accounts/**", "/resetPassword", "/savePassword", "/changePassword"};

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests(authz -> authz
                        .antMatchers(WHITE_LIST_URLS).permitAll()
                        .antMatchers(AUTHENTICATED_LIST_URLS).authenticated()
                        .anyRequest().authenticated()
                );

        httpSecurity.headers().frameOptions().disable();

        return httpSecurity.build();
    }
}
