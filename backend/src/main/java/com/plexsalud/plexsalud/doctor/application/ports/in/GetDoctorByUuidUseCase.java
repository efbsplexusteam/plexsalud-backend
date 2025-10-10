package com.plexsalud.plexsalud.doctor.application.ports.in;

import java.util.UUID;

import com.plexsalud.plexsalud.doctor.domain.models.Doctor;

public interface GetDoctorByUuidUseCase {
    Doctor getOneByUuid(UUID uuid);
}
