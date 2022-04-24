package com.example.springsecuritystudy;

import com.example.springsecuritystudy.domain.Account;
import com.example.springsecuritystudy.enums.UserRole;
import com.example.springsecuritystudy.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringSecurityStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityStudyApplication.class, args);
    }

    /**
     * 애플리케이션 구동 시 테스트용 계정 생성
     */
    @Bean
    CommandLineRunner bootstrapTestAccount(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Account account = new Account()
                    .setUsernmae("test_user")
                    .setUserId("user_id")
                    .setPassword(passwordEncoder.encode("1234"))
                    .setUserRole(UserRole.USER);

            accountRepository.save(account);
        };
    }
}
