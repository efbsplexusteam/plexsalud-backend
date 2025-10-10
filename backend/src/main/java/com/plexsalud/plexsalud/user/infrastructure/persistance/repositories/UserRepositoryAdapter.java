package com.plexsalud.plexsalud.user.infrastructure.persistance.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.plexsalud.plexsalud.common.mappers.UserMapper;
import com.plexsalud.plexsalud.user.application.ports.out.UserRepositoryPort;
import com.plexsalud.plexsalud.user.domain.models.Role;
import com.plexsalud.plexsalud.user.domain.models.User;
import com.plexsalud.plexsalud.user.infrastructure.persistance.entities.UserEntity;

@Repository
public class UserRepositoryAdapter implements UserRepositoryPort {
    private final UserEntityRepository userEntityRepository;

    public UserRepositoryAdapter(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    public Optional<User> findByEmail(String email) {
        return userEntityRepository.findByEmail(email).map(u -> UserMapper.toDomain(u));
    };

    public Optional<User> findByEmailAndRole(String email, Role role) {
        return userEntityRepository.findByEmailAndRole(email, role).map(u -> UserMapper.toDomain(u));
    };

    public User save(User user) {
        UserEntity userSaved = userEntityRepository.save(UserMapper.toEntity(user));
        return UserMapper.toDomain(userSaved);
    }

    public User findByUuid(UUID uuid) {
        return userEntityRepository.findById(uuid).map(u -> UserMapper.toDomain(u)).orElse(null);
    }

}
