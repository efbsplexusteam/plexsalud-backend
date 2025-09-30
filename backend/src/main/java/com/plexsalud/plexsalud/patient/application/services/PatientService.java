package com.plexsalud.plexsalud.patient.application.services;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.plexsalud.plexsalud.patient.application.dtos.PatientDto;
import com.plexsalud.plexsalud.patient.application.responses.PatientResponse;
import com.plexsalud.plexsalud.patient.domain.entities.Patient;
import com.plexsalud.plexsalud.patient.domain.exceptions.PatientNotFoundException;
import com.plexsalud.plexsalud.patient.infrastructure.repositories.PatientRepository;
import com.plexsalud.plexsalud.user.domain.entities.User;
import com.plexsalud.plexsalud.user.infrastructure.repositories.UserRepository;

@Service
public class PatientService {
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository, UserRepository userRepository) {
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    public PatientResponse save(PatientDto patientDto) {
        Patient patient = new Patient();

        User user = userRepository.findById(patientDto.getUserUuid())
                .orElseThrow(() -> new RuntimeException("Patient not found with uuid: " + patientDto.getUserUuid()));

        patient.setFullName(patientDto.getFullName());
        patient.setUser(user);

        patient = patientRepository.save(patient);
        PatientResponse PatientResponse = new PatientResponse(patient.getFullName());

        return PatientResponse;
    }

    public PatientResponse findOne(UUID uuid) {
        Patient patient = patientRepository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Patient not found with uuid: " + uuid));

        return new PatientResponse(patient.getFullName());
    }

    public Patient findOneRaw(UUID uuid) {
        return patientRepository.findByUser(new User().setUuid(uuid))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Patient not found with uuid: " + uuid));
    }

    public Patient findOneRawByUser(UUID uuid) {
        return patientRepository.findByUser(new User().setUuid(uuid))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Patient not found with user_uuid: " + uuid));
    }

    public PatientResponse findOneByUser(UUID uuid) {
        Patient patient = patientRepository.findByUser(new User().setUuid(uuid))
                .orElseThrow(() -> new PatientNotFoundException(
                        "Patient not found with uuid: " + uuid));

        // .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
        // "Patient not found with uuid: " + uuid));

        return new PatientResponse(patient.getFullName());
    }
}
