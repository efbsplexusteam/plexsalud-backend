package com.plexsalud.plexsalud.auth.infrastructure.responses;

import com.plexsalud.plexsalud.user.domain.entities.Role;

public class LoginResponse {
    private String accessToken;

    private Role role;

    public String getAccessToken() {
        return accessToken;
    }

    public LoginResponse setAccessToken(String token) {
        this.accessToken = token;
        return this;
    }

    public Role getRole() {
        return this.role;
    }

    public LoginResponse setRole(Role role) {
        this.role = role;
        return this;
    }
}
