package com.plexsalud.plexsalud.doctor.infrastructure.persistance.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.plexsalud.plexsalud.doctor.infrastructure.persistance.entities.DoctorEntity;
import com.plexsalud.plexsalud.user.infrastructure.persistance.entities.UserEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface DoctorEntityRepository extends CrudRepository<DoctorEntity, UUID> {
    Optional<DoctorEntity> findByUser(UserEntity user);

    @Query("SELECT DISTINCT d.specialty FROM DoctorEntity d")
    List<String> findDistinctSpecialty();

    List<DoctorEntity> findAllDoctorsBySpecialty(String specialty);
}
