package com.plexsalud.plexsalud.auth.application.ports.in;

import com.plexsalud.plexsalud.user.domain.entities.User;

public interface GenerateTokenUseCase {
    String generateTokenUseCase(User user);
}
