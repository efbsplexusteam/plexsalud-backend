package com.plexsalud.plexsalud.doctor.infrastructure.persistance.repositories;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.plexsalud.plexsalud.common.mappers.DoctorMapper;
import com.plexsalud.plexsalud.common.mappers.UserMapper;
import com.plexsalud.plexsalud.doctor.application.ports.out.DoctorRepositoryPort;
import com.plexsalud.plexsalud.doctor.domain.models.Doctor;
import com.plexsalud.plexsalud.doctor.infrastructure.persistance.entities.DoctorEntity;
import com.plexsalud.plexsalud.user.domain.models.User;

@Repository
public class DoctorRepositoryAdapter implements DoctorRepositoryPort {
    private final DoctorEntityRepository doctorEntityRepository;

    public DoctorRepositoryAdapter(DoctorEntityRepository doctorEntityRepository) {
        this.doctorEntityRepository = doctorEntityRepository;
    }

    public Doctor save(Doctor doctor) {
        DoctorEntity doctorSaved = doctorEntityRepository.save(DoctorMapper.toEntity(doctor));
        return DoctorMapper.toDomain(doctorSaved);
    };

    public Doctor findById(UUID uuid) {
        return doctorEntityRepository.findById(uuid)
                .map(d -> DoctorMapper.toDomain(d)).orElse(null);
    };

    public Doctor findByUser(User user) {
        return doctorEntityRepository.findByUser(UserMapper.toEntity(user)).map(d -> DoctorMapper.toDomain(d))
                .orElse(null);
    };

    public List<String> findDistinctSpecialty() {
        return doctorEntityRepository.findDistinctSpecialty();
    };

    public List<Doctor> findAllDoctorsBySpecialty(String specialty) {
        return doctorEntityRepository.findAllDoctorsBySpecialty(specialty).stream().map(d -> DoctorMapper.toDomain(d))
                .collect(Collectors.toList());
    };
}
