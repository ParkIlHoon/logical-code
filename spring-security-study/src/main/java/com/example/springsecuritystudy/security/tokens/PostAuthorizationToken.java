package com.example.springsecuritystudy.security.tokens;

import com.example.springsecuritystudy.security.AccountContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 인증 후 토큰
 */
public class PostAuthorizationToken extends UsernamePasswordAuthenticationToken {

    private PostAuthorizationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public static PostAuthorizationToken getTokenFromAccountContext(AccountContext accountContext) {
        return new PostAuthorizationToken(accountContext, accountContext.getPassword(), accountContext.getAuthorities());
    }

    public String getUsername() {
        return (String) super.getPrincipal();
    }

    public String getUserPassword() {
        return (String) super.getCredentials();
    }
}
