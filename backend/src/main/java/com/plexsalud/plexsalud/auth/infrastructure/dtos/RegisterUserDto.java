package com.plexsalud.plexsalud.auth.infrastructure.dtos;

import com.plexsalud.plexsalud.user.domain.models.Role;

import lombok.Data;

@Data
public class RegisterUserDto {
    private String email;

    private String password;

    private Role role;
}
