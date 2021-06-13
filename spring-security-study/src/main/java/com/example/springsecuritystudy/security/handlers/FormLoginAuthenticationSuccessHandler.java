package com.example.springsecuritystudy.security.handlers;

import com.example.springsecuritystudy.dtos.TokenDto;
import com.example.springsecuritystudy.security.AccountContext;
import com.example.springsecuritystudy.security.JwtFactory;
import com.example.springsecuritystudy.security.tokens.PostAuthorizationToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 인증 성공 시 후처리 핸들러
 */
@Component
@RequiredArgsConstructor
public class FormLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtFactory jwtFactory;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        // 인증 후 토큰 취득
        PostAuthorizationToken token = (PostAuthorizationToken) authentication;
        // 토큰에서 인증 정보 취득
        AccountContext accountContext = (AccountContext) token.getPrincipal();
        // 인증 정보를 기반으로 JWT 토큰 생성
        String generateToken = jwtFactory.generateToken(accountContext);

        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(generateToken);

        // JWT 토큰 응답 처리
        processResponse(httpServletResponse, tokenDto);
    }


    private void processResponse(HttpServletResponse response, TokenDto tokenDto) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(objectMapper.writeValueAsString(tokenDto));
    }
}
