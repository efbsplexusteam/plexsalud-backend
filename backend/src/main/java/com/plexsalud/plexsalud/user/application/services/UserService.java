package com.plexsalud.plexsalud.user.application.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.plexsalud.plexsalud.user.application.ports.in.GetUserByUuidUseCase;
import com.plexsalud.plexsalud.user.application.ports.out.UserRepositoryPort;
import com.plexsalud.plexsalud.user.domain.models.User;

@Service
public class UserService implements GetUserByUuidUseCase {

    private final UserRepositoryPort userRepositoryPort;

    public UserService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    public User getOne(UUID uuid) {
        return userRepositoryPort.findByUuid(uuid);
    }
}
