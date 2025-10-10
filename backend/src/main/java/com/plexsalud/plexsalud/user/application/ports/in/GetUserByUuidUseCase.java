package com.plexsalud.plexsalud.user.application.ports.in;

import java.util.UUID;

import com.plexsalud.plexsalud.user.domain.models.User;

public interface GetUserByUuidUseCase {
    User getOne(UUID uuid);
}
