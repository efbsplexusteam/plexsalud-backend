package com.plexsalud.plexsalud.nurse.infrastructure.persistance.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.plexsalud.plexsalud.nurse.infrastructure.persistance.entities.NurseEntity;
import com.plexsalud.plexsalud.user.infrastructure.persistance.entities.UserEntity;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface NurseEntityRepository extends CrudRepository<NurseEntity, UUID> {
    Optional<NurseEntity> findByUser(UserEntity user);
}
