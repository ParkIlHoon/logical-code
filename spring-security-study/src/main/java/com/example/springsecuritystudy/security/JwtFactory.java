package com.example.springsecuritystudy.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * JWT Token 생성 팩토리
 */
@Component
public class JwtFactory {

    private static String signingKey = "jwttest";

    public String generateToken(AccountContext accountContext) {
        String token = null;
        List<GrantedAuthority> authorities = accountContext.getAuthorities().stream().collect(Collectors.toList());
        token = JWT.create()
                .withIssuer("logical-code")
                .withClaim("USER_NAME", accountContext.getAccount().getUserId())
                .withClaim("USER_ROLE", accountContext.getAccount().getUserRole().getRoleName())
                .sign(generateAlgorithm());

        return token;
    }

    private Algorithm generateAlgorithm() {
        return Algorithm.HMAC256(signingKey);
    }
}
