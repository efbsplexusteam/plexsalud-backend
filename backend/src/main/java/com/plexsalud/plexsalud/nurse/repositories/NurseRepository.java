package com.plexsalud.plexsalud.nurse.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.plexsalud.plexsalud.nurse.entities.Nurse;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface NurseRepository extends CrudRepository<Nurse, UUID> {
    Optional<Nurse> findByEmail(String email);
}
