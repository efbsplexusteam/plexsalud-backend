package com.plexsalud.plexsalud.user.infrastructure.persistance.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.plexsalud.plexsalud.user.application.ports.UserRepositoryPort;
import com.plexsalud.plexsalud.user.domain.models.Role;
import com.plexsalud.plexsalud.user.domain.models.User;

@Repository
public class UserRepositoryAdapter implements UserRepositoryPort {
    private final UserEntityRepository userEntityRepository;

    public UserRepositoryAdapter(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    public Optional<User> findByEmail(String email) {
        return userEntityRepository.findByEmail(email).map(u -> {
            return new User(u.getUuid(), u.getUsername(), u.getPassword(), u.getRole());
        });
    };

    public Optional<User> findByEmailAndRole(String email, Role role) {
        return userEntityRepository.findByEmailAndRole(email, role).map(u -> {
            return new User(u.getUuid(), u.getUsername(), u.getPassword(), u.getRole());
        });
    };
}
