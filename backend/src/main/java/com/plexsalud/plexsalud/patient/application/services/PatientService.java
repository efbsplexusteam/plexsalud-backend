package com.plexsalud.plexsalud.patient.application.services;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.plexsalud.plexsalud.patient.application.ports.in.CreatePatientUseCase;
import com.plexsalud.plexsalud.patient.application.ports.in.GetPatientByUserUseCase;
import com.plexsalud.plexsalud.patient.application.ports.in.GetPatientByUuidUseCase;
import com.plexsalud.plexsalud.patient.application.ports.out.PatientRepositoryPort;
import com.plexsalud.plexsalud.patient.domain.models.Patient;
import com.plexsalud.plexsalud.user.domain.models.User;

@Service
public class PatientService implements CreatePatientUseCase, GetPatientByUuidUseCase, GetPatientByUserUseCase {

    private final PatientRepositoryPort patientRepositoryPort;

    public PatientService(PatientRepositoryPort patientRepositoryPort) {
        this.patientRepositoryPort = patientRepositoryPort;
    }

    public Patient save(Patient patient) {
        return patientRepositoryPort.save(patient);
    }

    public Patient getByUser(User user) {
        return patientRepositoryPort.findByUser(user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Patient not found with user_uuid: " + user.getUuid()));
    }

    public Patient getOne(UUID uuid) {
        return patientRepositoryPort.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Patient not found with user_uuid: " + uuid));
    }
}
