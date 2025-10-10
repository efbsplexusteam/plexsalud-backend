package com.plexsalud.plexsalud.patient.application.ports.out;

import java.util.Optional;
import java.util.UUID;

import com.plexsalud.plexsalud.patient.domain.models.Patient;
import com.plexsalud.plexsalud.user.domain.models.User;

public interface PatientRepositoryPort {
    Patient save(Patient patient);

    Optional<Patient> findById(UUID uuid);

    Optional<Patient> findByUser(User user);
}
