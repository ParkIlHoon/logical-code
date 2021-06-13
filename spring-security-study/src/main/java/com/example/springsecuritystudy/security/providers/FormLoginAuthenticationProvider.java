package com.example.springsecuritystudy.security.providers;

import com.example.springsecuritystudy.domain.Account;
import com.example.springsecuritystudy.repository.AccountRepository;
import com.example.springsecuritystudy.security.AccountContext;
import com.example.springsecuritystudy.security.AccountContextService;
import com.example.springsecuritystudy.security.tokens.PostAuthorizationToken;
import com.example.springsecuritystudy.security.tokens.PreAuthorizationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;


public class FormLoginAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AccountContextService accountContextService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        PreAuthorizationToken preAuthorizationToken = (PreAuthorizationToken) authentication;

        String username = preAuthorizationToken.getUsername();
        String userPassword = preAuthorizationToken.getUserPassword();

        Account account = accountRepository.findByUserId(username).orElseThrow(NoSuchElementException::new);
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
        return passwordEncoder.matches(account.getPassword(), password);
    }
}
