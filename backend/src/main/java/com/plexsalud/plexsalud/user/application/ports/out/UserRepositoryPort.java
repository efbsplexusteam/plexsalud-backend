package com.plexsalud.plexsalud.user.application.ports.out;

import java.util.Optional;
import java.util.UUID;

import com.plexsalud.plexsalud.user.domain.models.Role;
import com.plexsalud.plexsalud.user.domain.models.User;

public interface UserRepositoryPort {
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndRole(String email, Role role);

    User findByUuid(UUID uuid);

    User save(User user);
}
