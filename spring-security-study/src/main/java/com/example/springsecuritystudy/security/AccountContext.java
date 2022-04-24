package com.example.springsecuritystudy.security;

import com.example.springsecuritystudy.domain.Account;
import com.example.springsecuritystudy.enums.UserRole;
import com.example.springsecuritystudy.security.tokens.JwtPostProcessingToken;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AccountContext extends User {

    private Account account;

    public AccountContext(Account account, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.account = account;
    }

    public AccountContext(String userName, String username, String userRole) {
        super(userName, username, parseAuthorities(userRole));
    }

    public static AccountContext fromAccountModel(Account account) {
        return new AccountContext(account, account.getUserId(), account.getPassword(), parseAuthorities(account.getUserRole()));
    }

    public static AccountContext fromJwtPostToken(JwtPostProcessingToken token) {
        return new AccountContext(null, token.getUserId(), token.getUserPassword(), token.getAuthorities());
    }

    public static List<SimpleGrantedAuthority> parseAuthorities(UserRole userRole) {
        return Arrays.asList(userRole).stream().map(r -> new SimpleGrantedAuthority(r.getRoleName())).collect(Collectors.toList());
    }

    public static List<SimpleGrantedAuthority> parseAuthorities(String roleName) {
        return parseAuthorities(UserRole.getRoleByName(roleName));
    }
}
