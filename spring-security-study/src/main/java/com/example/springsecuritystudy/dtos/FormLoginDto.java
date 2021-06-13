package com.example.springsecuritystudy.dtos;

import lombok.Data;

@Data
public class FormLoginDto {
    private String userId;
    private String password;
}
