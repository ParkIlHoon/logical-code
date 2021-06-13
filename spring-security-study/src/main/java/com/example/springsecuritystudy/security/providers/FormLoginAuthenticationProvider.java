package com.example.springsecuritystudy.security.providers;

import com.example.springsecuritystudy.domain.Account;
import com.example.springsecuritystudy.repository.AccountRepository;
import com.example.springsecuritystudy.security.AccountContext;
import com.example.springsecuritystudy.security.AccountContextService;
import com.example.springsecuritystudy.security.tokens.PostAuthorizationToken;
import com.example.springsecuritystudy.security.tokens.PreAuthorizationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class FormLoginAuthenticationProvider implements AuthenticationProvider {

    private final AccountContextService accountContextService;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 인증 처리
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 인증 전 토큰 취득
        PreAuthorizationToken preAuthorizationToken = (PreAuthorizationToken) authentication;

        String username = preAuthorizationToken.getUsername();
        String userPassword = preAuthorizationToken.getUserPassword();

        // 사용자 조회
        Account account = accountRepository.findByUserId(username).orElseThrow(NoSuchElementException::new);
        // 사용자/패스워드 일치 시 인증 후 토큰 생성 및 반환
        if (isCorrectPassword(userPassword, account)) {
            return PostAuthorizationToken.getTokenFromAccountContext(AccountContext.fromAccountModel(account));
        } else {
            throw new NoSuchElementException("인증 정보가 정확하지 않습니다.");
        }
    }

    /**
     * 어떤 인증 객체를 지원할 지 지정
     * PreAuthorizationToken 으로 들어오는 요청은 이 Provider에서 처리
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return PreAuthorizationToken.class.isAssignableFrom(aClass);
    }

    private boolean isCorrectPassword(String password, Account account) {
        return passwordEncoder.matches(password, account.getPassword());
    }
}
