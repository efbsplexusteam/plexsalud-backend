package com.plexsalud.plexsalud.auth.responses;

public class LoginResponse {
    private String accessToken;

    private Integer role;

    private long expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public LoginResponse setAccessToken(String token) {
        this.accessToken = token;
        return this;
    }

    public Integer getRole() {
        return this.role;
    }

    public LoginResponse setRole(Integer role) {
        this.role = role;
        return this;
    }

    public LoginResponse setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    // Getters and setters...
}
