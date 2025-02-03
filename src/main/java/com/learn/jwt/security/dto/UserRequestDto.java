package com.learn.jwt.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    private String name;

    private String email;

    private String password;

    private String phone;

    private String address;

}
