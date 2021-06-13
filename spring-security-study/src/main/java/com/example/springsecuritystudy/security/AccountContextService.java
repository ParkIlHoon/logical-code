package com.example.springsecuritystudy.security;

import com.example.springsecuritystudy.domain.Account;
import com.example.springsecuritystudy.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class AccountContextService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Account account = accountRepository.findByUserId(userId).orElseThrow(NoSuchElementException::new);
        return getAccountContext(account);
    }

    private AccountContext getAccountContext(Account account) {
        return AccountContext.fromAccountModel(account);
    }
}
