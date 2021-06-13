package com.example.springsecuritystudy.repository;

import com.example.springsecuritystudy.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUserId(String userId);

    Optional<Account> findBySocialId(String socialId);

}
