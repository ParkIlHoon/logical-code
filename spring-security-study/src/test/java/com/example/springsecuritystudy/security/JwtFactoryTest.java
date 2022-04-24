package com.example.springsecuritystudy.security;

import com.example.springsecuritystudy.domain.Account;
import com.example.springsecuritystudy.enums.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class JwtFactoryTest {
    @Autowired
    JwtFactory jwtFactory;

    private AccountContext accountContext;

    @BeforeEach
    void setUp() {
        accountContext = AccountContext.fromAccountModel(new Account().setUserId("user_id").setPassword("1234").setUserRole(UserRole.USER));
    }

    @Test
    void JWT_토큰_생성_성공() {
        // when
        String generateToken = jwtFactory.generateToken(this.accountContext);

        // then
        assertNotNull(generateToken);
        log.info(generateToken);
    }

    @Test
    void JWT_토큰_생성_실패_권한없음() {
        // given & when & then
        assertThrows(NullPointerException.class, () -> {
            accountContext = AccountContext.fromAccountModel(new Account());
            String generateToken = jwtFactory.generateToken(this.accountContext);
        });
    }
}