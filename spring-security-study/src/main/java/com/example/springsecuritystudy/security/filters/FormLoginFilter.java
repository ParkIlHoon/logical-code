package com.example.springsecuritystudy.security.filters;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 1. attemptAuthentication 에서 인증 시도 (FormLoginAuthenticationProvider.authenticate 메서드 호출)
 * 2. 인증 성공 시 successfulAuthentication 에서 JWT 토큰 생성
 */
public class FormLoginFilter extends AbstractAuthenticationProcessingFilter {
    protected FormLoginFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
