package com.plexsalud.plexsalud.user.domain.models;

import java.util.UUID;

public record User(
        UUID uuid,
        String email,
        String password,
        Role role) {
}
