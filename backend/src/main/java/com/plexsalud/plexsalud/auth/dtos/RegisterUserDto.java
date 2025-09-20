package com.plexsalud.plexsalud.auth.dtos;

import com.plexsalud.plexsalud.user.entities.Role;

import lombok.Data;

@Data
public class RegisterUserDto {
    private String email;

    private String password;

    private Role role;
}
