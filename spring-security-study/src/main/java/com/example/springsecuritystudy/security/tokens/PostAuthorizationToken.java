package com.example.springsecuritystudy.security.tokens;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class PostAuthorizationToken extends UsernamePasswordAuthenticationToken {

    public PostAuthorizationToken(String username, String password) {
        super(username, password);
    }

    public String getUsername() {
        return (String) super.getPrincipal();
    }

    public String getUserPassword() {
        return (String) super.getCredentials();
    }
}
