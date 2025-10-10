package com.plexsalud.plexsalud.auth.application.ports.in;

import com.plexsalud.plexsalud.user.domain.models.User;

public interface AuthenticateUserUseCase {
    User authenticate(User user);
}
