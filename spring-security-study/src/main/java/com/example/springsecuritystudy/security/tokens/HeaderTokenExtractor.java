package com.example.springsecuritystudy.security.tokens;

import com.example.springsecuritystudy.security.exception.InvalidJwtTokenException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class HeaderTokenExtractor {
    public static final String HEADER_TOKEN_PREFIX = "Bearer ";

    public String extractToken(String header) {
        if (!StringUtils.hasText(header) || header.length() < HEADER_TOKEN_PREFIX.length()) {
            throw new InvalidJwtTokenException("올바르지 않은 토큰입니다.");
        }
        return header.substring(HEADER_TOKEN_PREFIX.length(), header.length());
    }
}
