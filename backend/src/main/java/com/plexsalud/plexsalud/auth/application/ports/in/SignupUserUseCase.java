package com.plexsalud.plexsalud.auth.application.ports.in;

import com.plexsalud.plexsalud.auth.infrastructure.dtos.RegisterUserDto;
import com.plexsalud.plexsalud.auth.infrastructure.responses.RegisterResponse;

public interface SignupUserUseCase {
    RegisterResponse signup(RegisterUserDto registerUserDto);
}
