package com.plexsalud.plexsalud.patient.infrastructure.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.plexsalud.plexsalud.patient.domain.entities.Patient;
import com.plexsalud.plexsalud.user.domain.entities.User;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface PatientRepository extends CrudRepository<Patient, UUID> {
    Optional<Patient> findByUser(User user);
}
