package com.plexsalud.plexsalud.nurse.infrastructure.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.plexsalud.plexsalud.nurse.domain.entities.Nurse;
import com.plexsalud.plexsalud.user.domain.entities.User;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface NurseRepository extends CrudRepository<Nurse, UUID> {
    Optional<Nurse> findByUser(User user);
}
