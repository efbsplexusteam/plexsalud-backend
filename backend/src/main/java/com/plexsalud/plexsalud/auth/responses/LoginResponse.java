package com.plexsalud.plexsalud.auth.responses;
import com.plexsalud.plexsalud.user.entities.Role;

public class LoginResponse {
    private String accessToken;

    private Role role;

    private long expiresIn;

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

    public LoginResponse setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

}
