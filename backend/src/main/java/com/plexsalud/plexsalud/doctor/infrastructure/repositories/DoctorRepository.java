package com.plexsalud.plexsalud.doctor.infrastructure.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.plexsalud.plexsalud.doctor.application.dtos.DoctorFullNameAndUuidDto;
import com.plexsalud.plexsalud.doctor.domain.entities.Doctor;
import com.plexsalud.plexsalud.user.domain.entities.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, UUID> {
    Optional<Doctor> findByUser(User user);

    @Query("SELECT DISTINCT d.specialty FROM Doctor d")
    List<String> findDistinctSpecialty();

    @Query("SELECT d.fullName, d.uuid FROM Doctor d WHERE d.specialty = :specialty")
    List<DoctorFullNameAndUuidDto> findAllDoctorsBySpecialty(String specialty);
}
