package com.plexsalud.plexsalud.auth.application.ports.in;

import com.plexsalud.plexsalud.auth.infrastructure.dtos.LoginUserDto;
import com.plexsalud.plexsalud.user.domain.entities.User;

public interface AuthenticateUserUseCase {
    User authenticate(LoginUserDto loginUserDto);
}
