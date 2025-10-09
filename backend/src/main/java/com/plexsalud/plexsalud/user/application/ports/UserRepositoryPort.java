package com.plexsalud.plexsalud.user.application.ports;

import java.util.Optional;

import com.plexsalud.plexsalud.user.domain.models.Role;
import com.plexsalud.plexsalud.user.domain.models.User;

public interface UserRepositoryPort {
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndRole(String email, Role role);
}
