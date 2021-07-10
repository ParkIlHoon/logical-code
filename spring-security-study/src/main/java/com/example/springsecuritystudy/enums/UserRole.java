package com.example.springsecuritystudy.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public boolean isCorrectName(String name) {
        return name.equalsIgnoreCase(this.roleName);
    }

    public static UserRole getRoleByName(String name) {
        return Arrays.stream(UserRole.values()).filter(r -> r.isCorrectName(name)).findFirst().orElseThrow(NoSuchElementException::new);
    }
}
