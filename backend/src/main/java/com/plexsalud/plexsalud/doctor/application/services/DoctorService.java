package com.plexsalud.plexsalud.doctor.application.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.plexsalud.plexsalud.doctor.application.ports.in.CreateDoctorUseCase;
import com.plexsalud.plexsalud.doctor.application.ports.in.GetAllDoctorsBySpecialtyUseCase;
import com.plexsalud.plexsalud.doctor.application.ports.in.GetAllSpecialtiesUseCase;
import com.plexsalud.plexsalud.doctor.application.ports.in.GetDoctorByUserUseCase;
import com.plexsalud.plexsalud.doctor.application.ports.in.GetDoctorByUuidUseCase;
import com.plexsalud.plexsalud.doctor.application.ports.out.DoctorRepositoryPort;
import com.plexsalud.plexsalud.doctor.domain.models.Doctor;
import com.plexsalud.plexsalud.user.domain.models.User;

@Service
public class DoctorService implements CreateDoctorUseCase, GetAllDoctorsBySpecialtyUseCase, GetAllSpecialtiesUseCase,
        GetDoctorByUserUseCase, GetDoctorByUuidUseCase {

    private final DoctorRepositoryPort doctorRepositoryPort;

    public DoctorService(
            DoctorRepositoryPort doctorRepositoryPort) {

        this.doctorRepositoryPort = doctorRepositoryPort;
    }

    public Doctor save(Doctor doctor) {
        return doctorRepositoryPort.save(doctor);
    }

    public List<Doctor> getDoctors(String specialty) {
        return doctorRepositoryPort.findAllDoctorsBySpecialty(specialty);
    }

    public List<String> getSpecialties() {
        return doctorRepositoryPort.findDistinctSpecialty();
    }

    public Doctor getDoctor(User user) {
        return doctorRepositoryPort.findByUser(user);
    }

    public Doctor getOneByUuid(UUID uuid) {
        return doctorRepositoryPort.findById(uuid);
    }
}
