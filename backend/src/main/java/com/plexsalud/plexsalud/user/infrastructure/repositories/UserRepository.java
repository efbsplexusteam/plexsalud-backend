package com.plexsalud.plexsalud.user.infrastructure.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.plexsalud.plexsalud.user.domain.entities.Role;
import com.plexsalud.plexsalud.user.domain.entities.User;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndRole(String email, Role role);
}
