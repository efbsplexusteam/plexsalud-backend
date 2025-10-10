package com.plexsalud.plexsalud.patient.infrastructure.persistance.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.plexsalud.plexsalud.common.mappers.PatientMapper;
import com.plexsalud.plexsalud.common.mappers.UserMapper;
import com.plexsalud.plexsalud.patient.application.ports.out.PatientRepositoryPort;
import com.plexsalud.plexsalud.patient.domain.models.Patient;
import com.plexsalud.plexsalud.patient.infrastructure.persistance.entities.PatientEntity;
import com.plexsalud.plexsalud.user.domain.models.User;

@Repository
public class PatientRepositoryAdapter implements PatientRepositoryPort {
    private final PatientEntityRepository patientEntityRepository;

    public PatientRepositoryAdapter(PatientEntityRepository patientEntityRepository) {
        this.patientEntityRepository = patientEntityRepository;
    }

    public Patient save(Patient patient) {
        PatientEntity patientEntity = patientEntityRepository.save(PatientMapper.toEntity(patient));
        return PatientMapper.toDomain(patientEntity);
    };

    public Optional<Patient> findById(UUID uuid) {
        return patientEntityRepository.findById(uuid).map(p -> PatientMapper.toDomain(p));
    };

    public Optional<Patient> findByUser(User user) {
        return patientEntityRepository.findByUser(UserMapper.toEntity(user)).map(p -> PatientMapper.toDomain(p));
    };
}
