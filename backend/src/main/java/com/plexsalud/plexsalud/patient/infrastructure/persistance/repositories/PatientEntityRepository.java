package com.plexsalud.plexsalud.patient.infrastructure.persistance.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.plexsalud.plexsalud.patient.infrastructure.persistance.entities.PatientEntity;
import com.plexsalud.plexsalud.user.infrastructure.persistance.entities.UserEntity;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface PatientEntityRepository extends CrudRepository<PatientEntity, UUID> {
    Optional<PatientEntity> findByUser(UserEntity user);
}
