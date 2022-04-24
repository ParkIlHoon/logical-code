package com.example.springsecuritystudy.security.tokens;

import com.example.springsecuritystudy.enums.UserRole;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class JwtPostProcessingToken extends UsernamePasswordAuthenticationToken {

    private JwtPostProcessingToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public JwtPostProcessingToken(String username, UserRole role) {
        super(username, "1234", parseAuthorities(role));
    }

    private static Collection<? extends GrantedAuthority> parseAuthorities(UserRole userRole) {
        return Arrays.asList(userRole).stream().map(r -> new SimpleGrantedAuthority(r.getRoleName())).collect(Collectors.toList());
    }

    public String getUserId() {
        return (String) super.getPrincipal();
    }

    public String getUserPassword() {
        return (String) super.getCredentials();
    }
}
