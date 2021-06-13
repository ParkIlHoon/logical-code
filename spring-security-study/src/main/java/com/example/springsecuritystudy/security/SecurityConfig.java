package com.example.springsecuritystudy.security;

import com.example.springsecuritystudy.security.filters.FormLoginFilter;
import com.example.springsecuritystudy.security.handlers.FormLoginAuthenticationFailureHandler;
import com.example.springsecuritystudy.security.handlers.FormLoginAuthenticationSuccessHandler;
import com.example.springsecuritystudy.security.providers.FormLoginAuthenticationProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private FormLoginAuthenticationSuccessHandler formLoginAuthenticationSuccessHandler;
    @Autowired
    private FormLoginAuthenticationFailureHandler formLoginAuthenticationFailureHandler;
    @Autowired
    private FormLoginAuthenticationProvider formLoginAuthenticationProvider;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    public FormLoginFilter getFormLoginFilter() throws Exception {
        FormLoginFilter formLoginFilter = new FormLoginFilter("/form-login",
                formLoginAuthenticationSuccessHandler,
                formLoginAuthenticationFailureHandler);
        formLoginFilter.setAuthenticationManager(getAuthenticationManager());
        return formLoginFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(formLoginAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(getFormLoginFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
