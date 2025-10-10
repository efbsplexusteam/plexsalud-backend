package com.plexsalud.plexsalud.patient.application.ports.in;

import com.plexsalud.plexsalud.patient.domain.models.Patient;

public interface CreatePatientUseCase {
    public Patient save(Patient patient);
}
