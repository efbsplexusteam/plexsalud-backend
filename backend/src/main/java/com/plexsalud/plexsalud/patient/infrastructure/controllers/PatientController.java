package com.plexsalud.plexsalud.patient.infrastructure.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.plexsalud.plexsalud.auth.application.ports.in.ExtractRoleUseCase;
import com.plexsalud.plexsalud.auth.application.ports.in.ExtractUuidUseCase;
import com.plexsalud.plexsalud.patient.application.dtos.PatientDto;
import com.plexsalud.plexsalud.patient.application.ports.in.CreatePatientUseCase;
import com.plexsalud.plexsalud.patient.application.ports.in.GetPatientByUserUseCase;
import com.plexsalud.plexsalud.patient.application.ports.in.GetPatientByUuidUseCase;
import com.plexsalud.plexsalud.patient.application.responses.PatientResponse;
import com.plexsalud.plexsalud.patient.domain.exceptions.PatientNotFoundException;
import com.plexsalud.plexsalud.patient.domain.models.Patient;
import com.plexsalud.plexsalud.user.application.ports.in.GetUserByUuidUseCase;
import com.plexsalud.plexsalud.user.domain.models.Role;
import com.plexsalud.plexsalud.user.domain.models.User;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Patients", description = "Operations with patients")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/patients")
@RestController
public class PatientController {

    private final ExtractUuidUseCase extractUuidUseCase;
    private final ExtractRoleUseCase extractRoleUseCase;
    private final GetUserByUuidUseCase getUserByUuidUseCase;
    private final CreatePatientUseCase createPatientUseCase;
    private final GetPatientByUserUseCase getPatientByUserUseCase;
    private final GetPatientByUuidUseCase getPatientByUuidUseCase;

    public PatientController(
            ExtractUuidUseCase extractUuidUseCase,
            ExtractRoleUseCase extractRoleUseCase,
            GetUserByUuidUseCase getUserByUuidUseCase,
            CreatePatientUseCase createPatientUseCase,
            GetPatientByUserUseCase getPatientByUserUseCase,
            GetPatientByUuidUseCase getPatientByUuidUseCase) {
        this.extractUuidUseCase = extractUuidUseCase;
        this.extractRoleUseCase = extractRoleUseCase;
        this.getUserByUuidUseCase = getUserByUuidUseCase;
        this.createPatientUseCase = createPatientUseCase;
        this.getPatientByUserUseCase = getPatientByUserUseCase;
        this.getPatientByUuidUseCase = getPatientByUuidUseCase;
    }

    @PostMapping
    public ResponseEntity<PatientResponse> savePatient(HttpServletRequest request,
            @RequestBody PatientDto patientDto) {
        UUID uuid = extractUuidUseCase.extractUuid(request);
        Role role = extractRoleUseCase.extractRole(request);

        if (role != Role.PATIENT) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role Invalid");
        }

        User user = getUserByUuidUseCase.getOne(uuid);
        Patient patient = new Patient().setUser(user).setFullName(patientDto.getFullName());
        Patient patientSaved = createPatientUseCase.save(patient);
        PatientResponse patientResponse = new PatientResponse(patientSaved.getFullName());
        return ResponseEntity.ok(patientResponse);
    }

    @GetMapping("self")
    public ResponseEntity<PatientResponse> findSelf(HttpServletRequest request) {
        UUID uuid = extractUuidUseCase.extractUuid(request);

        Patient patient = getPatientByUserUseCase.getByUser(new User().setUuid(uuid));

        if (patient == null) {
            throw new PatientNotFoundException("Patient not found");
        }

        PatientResponse patientResponse = new PatientResponse(patient.getFullName());

        return ResponseEntity.ok(patientResponse);
    }

    @GetMapping("uuid")
    public PatientResponse findOne(@RequestParam("uuid") UUID uuid) {
        Patient patient = getPatientByUuidUseCase.getOne(uuid);
        return new PatientResponse(patient.getFullName());
    }

}
