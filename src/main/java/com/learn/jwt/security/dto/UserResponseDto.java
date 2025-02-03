package com.learn.jwt.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private Long id;

    private String name;

    private String email;

    private String password;

    private String phone;

    private String address;

    private boolean active;

    private LocalDateTime createdAt;

    private List<RoleDto> roles;
}
