package com.plexsalud.plexsalud.patient.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.plexsalud.plexsalud.user.entities.User;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface PatientRepository extends CrudRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
