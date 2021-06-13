package com.example.springsecuritystudy.security.tokens;

import com.example.springsecuritystudy.dtos.FormLoginDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * 인증 전 토큰
 */
public class PreAuthorizationToken extends UsernamePasswordAuthenticationToken {

    public PreAuthorizationToken(String username, String password) {
        super(username, password);
    }

    public PreAuthorizationToken(FormLoginDto formLoginDto) {
        super(formLoginDto.getUserId(), formLoginDto.getPassword());
    }

    public String getUsername() {
        return (String) super.getPrincipal();
    }

    public String getUserPassword() {
        return (String) super.getCredentials();
    }
}
