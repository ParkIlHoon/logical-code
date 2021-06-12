package com.example.springsecuritystudy.domain;

import com.example.springsecuritystudy.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usernmae;

    private String userId;

    private String password;

    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

    private String socialId;
}
