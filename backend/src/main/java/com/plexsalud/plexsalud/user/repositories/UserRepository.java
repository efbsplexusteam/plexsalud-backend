package com.plexsalud.plexsalud.user.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.plexsalud.plexsalud.user.entities.User;
import com.plexsalud.plexsalud.user.entities.Role;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndRole(String email, Role role);
}
