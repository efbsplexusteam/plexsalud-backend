package com.plexsalud.plexsalud.doctor.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.plexsalud.plexsalud.doctor.entities.Doctor;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, UUID> {
    Optional<Doctor> findByEmail(String email);
}
