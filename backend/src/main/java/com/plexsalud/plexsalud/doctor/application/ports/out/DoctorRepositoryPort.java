package com.plexsalud.plexsalud.doctor.application.ports.out;

import java.util.List;
import java.util.UUID;

import com.plexsalud.plexsalud.doctor.domain.models.Doctor;
import com.plexsalud.plexsalud.user.domain.models.User;

public interface DoctorRepositoryPort {
    public Doctor save(Doctor doctor);

    public Doctor findById(UUID uuid);

    public Doctor findByUser(User user);

    public List<String> findDistinctSpecialty();

    public List<Doctor> findAllDoctorsBySpecialty(String specialty);
}
