package com.example.springsecuritystudy.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.springsecuritystudy.security.exception.InvalidJwtTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class JwtDecoder {

    private static String signingKey = "jwttest";

    public AccountContext decodeJwt(String token) {
        DecodedJWT decodedJWT = isValidToken(token).orElseThrow(() -> new InvalidJwtTokenException("유효하지 않은 토큰입니다."));

        String userName = decodedJWT.getClaim("USER_NAME").asString();
        String userRole = decodedJWT.getClaim("USER_ROLE").asString();

        return new AccountContext(userName, "1234", userRole);
    }

    private Optional<DecodedJWT> isValidToken(String token) {
        DecodedJWT jwt = null;

        Algorithm algorithm = Algorithm.HMAC256(signingKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        jwt = verifier.verify(token);

        return Optional.ofNullable(jwt);
    }
}
