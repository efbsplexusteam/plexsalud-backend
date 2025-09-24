package com.plexsalud.plexsalud.doctor.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.plexsalud.plexsalud.doctor.dtos.DoctorDto;
import com.plexsalud.plexsalud.doctor.dtos.DoctorFullNameAndUuidDto;
import com.plexsalud.plexsalud.doctor.entities.Doctor;
import com.plexsalud.plexsalud.doctor.exceptions.DoctorNotFoundException;
import com.plexsalud.plexsalud.doctor.reponses.DoctorResponse;
import com.plexsalud.plexsalud.doctor.repositories.DoctorRepository;
import com.plexsalud.plexsalud.user.entities.User;
import com.plexsalud.plexsalud.user.repositories.UserRepository;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    public DoctorService(DoctorRepository doctorRepository, UserRepository userRepository) {
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
    }

    public DoctorResponse save(DoctorDto doctorDto) {
        Doctor doctor = new Doctor();

        User user = userRepository.findById(doctorDto.getUserUuid())
                .orElseThrow(() -> new RuntimeException("User not found with uuid: " + doctorDto.getUserUuid()));

        doctor.setFullName(doctorDto.getFullName());
        doctor.setSpecialty(doctorDto.getSpecialty());
        doctor.setUser(user);

        doctor = doctorRepository.save(doctor);
        DoctorResponse doctorResponse = new DoctorResponse(doctor.getFullName(), doctor.getSpecialty());

        return doctorResponse;
    }

    public DoctorResponse findOne(UUID uuid) {
        Doctor doctor = doctorRepository.findById(uuid)
                .orElseThrow(() -> new DoctorNotFoundException(
                        "Doctor not found with uuid: " + uuid));

        return new DoctorResponse(doctor.getFullName(), doctor.getSpecialty());
    }

    public Doctor findOneRaw(UUID uuid) {
        return doctorRepository.findById(uuid)
                .orElseThrow(() -> new DoctorNotFoundException(
                        "Doctor not found with uuid: " + uuid));
    }

    public Doctor findOneRawByUser(UUID uuid) {
        return doctorRepository.findByUser(new User().setUuid(uuid))
                .orElseThrow(() -> new DoctorNotFoundException(
                        "Doctor not found with user_uuid: " + uuid));
    }

    public DoctorResponse findOneByUser(UUID uuid) {
        Doctor doctor = doctorRepository.findByUser(new User().setUuid(uuid))
                .orElseThrow(() -> new DoctorNotFoundException(
                        "Doctor not found with uuid: " + uuid));

        return new DoctorResponse(doctor.getFullName(), doctor.getSpecialty());
    }

    public List<String> findAllSpecialties() {
        List<String> specialties = doctorRepository.findDistinctSpecialty();

        return specialties;
    }

    public List<DoctorFullNameAndUuidDto> findAllDoctorsBySpecialty(String specialty) {
        List<DoctorFullNameAndUuidDto> doctors = doctorRepository.findAllDoctorsBySpecialty(specialty);

        return doctors;
    }
}
