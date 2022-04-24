package com.example.springsecuritystudy.security.tokens;

import com.example.springsecuritystudy.security.exception.InvalidJwtTokenException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HeaderTokenExtractorTest {
    @Autowired
    HeaderTokenExtractor headerTokenExtractor;

    @Test
    void JWT_토큰_검증_성공() {
        // given
        String headerValue = "asdf.asdf.asdf";
        String header = HeaderTokenExtractor.HEADER_TOKEN_PREFIX + headerValue;

        // when
        String extractToken = headerTokenExtractor.extractToken(header);

        // then
        assertEquals(headerValue, extractToken);
    }

    @Test
    void JWT_토큰_검증_실패() {
        // given
        String header = "";

        // when & then
        assertThrows(InvalidJwtTokenException.class, () -> {
           headerTokenExtractor.extractToken(header);
        });
    }
}