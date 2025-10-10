package com.plexsalud.plexsalud.patient.application.ports.in;

import java.util.UUID;

import com.plexsalud.plexsalud.patient.domain.models.Patient;

public interface GetPatientByUuidUseCase {
    Patient getOne(UUID uuid);
}
