package com.example.springsecuritystudy.security.filters;

import com.example.springsecuritystudy.dtos.FormLoginDto;
import com.example.springsecuritystudy.security.tokens.PreAuthorizationToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 1. attemptAuthentication 에서 인증 시도 (FormLoginAuthenticationProvider.authenticate 메서드 호출)
 * 2. 인증 성공 시 successfulAuthentication 에서 JWT 토큰 생성
 *
 * @Component로 구현하지 않는 이유? 프로세싱 url이 바뀌어서
 */
public class FormLoginFilter extends AbstractAuthenticationProcessingFilter {

    private AuthenticationSuccessHandler successHandler;
    private AuthenticationFailureHandler failureHandler;

    public FormLoginFilter(String defaultFilterProcessesUrl,
                           AuthenticationSuccessHandler successHandler,
                           AuthenticationFailureHandler failureHandler) {
        super(defaultFilterProcessesUrl);
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }

    protected FormLoginFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    /**
     * 인증을 시도한다.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        // RequestBody(JSON String) -> FormLoginDto
        FormLoginDto formLoginDto = new ObjectMapper().readValue(httpServletRequest.getReader(), FormLoginDto.class);
        // 미인증 토큰 생성
        PreAuthorizationToken preAuthorizationToken = new PreAuthorizationToken(formLoginDto);
        // 인증 시도
        return super.getAuthenticationManager().authenticate(preAuthorizationToken);
    }

    /**
     * 인증에 성공하면, 주입받은 successHandler에게 이후 처리를 위임한다.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        this.successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    /**
     * 인증에 실패하면, 주입받은 failureHandler에게 이후 처리를 위임한다.
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        this.failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
