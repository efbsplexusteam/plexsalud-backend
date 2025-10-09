package com.plexsalud.plexsalud.user.infrastructure.persistance.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.plexsalud.plexsalud.user.domain.models.Role;
import com.plexsalud.plexsalud.user.infrastructure.persistance.entities.UserEntity;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface UserEntityRepository extends CrudRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByEmailAndRole(String email, Role role);
}
