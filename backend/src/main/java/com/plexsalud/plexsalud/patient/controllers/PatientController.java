package com.plexsalud.plexsalud.patient.controllers;

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

import com.plexsalud.plexsalud.auth.services.JwtService;
import com.plexsalud.plexsalud.patient.dtos.PatientDto;
import com.plexsalud.plexsalud.patient.responses.PatientResponse;
import com.plexsalud.plexsalud.patient.services.PatientService;
import com.plexsalud.plexsalud.user.entities.Role;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Patients", description = "Operations with patients")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/patients")
@RestController
public class PatientController {

    private final PatientService patientService;
    private final JwtService jwtService;

    public PatientController(PatientService patientService, JwtService jwtService) {
        this.patientService = patientService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<PatientResponse> savePatient(HttpServletRequest request,
            @RequestBody PatientDto patientDto) {
        UUID uuid = jwtService.extractUuid(request);
        Role role = jwtService.extractRole(request);

        if (role != Role.PATIENT) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role Invalid");
        }

        patientDto.setUserUuid(uuid);
        PatientResponse saved = patientService.save(patientDto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("self")
    public ResponseEntity<PatientResponse> findSelf(HttpServletRequest request) {
        UUID uuid = jwtService.extractUuid(request);

        PatientResponse patientResponse = patientService.findOneByUser(uuid);

        return ResponseEntity.ok(patientResponse);
    }

    @GetMapping("uuid")
    public ResponseEntity<PatientResponse> findOne(@RequestParam("uuid") UUID uuid) {
        PatientResponse patientResponse = patientService.findOne(uuid);

        return ResponseEntity.ok(patientResponse);
    }

}
