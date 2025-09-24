package com.plexsalud.plexsalud.appointment.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.plexsalud.plexsalud.appointment.dtos.AppointmentDto;
import com.plexsalud.plexsalud.appointment.reponses.AppointmentResponse;
import com.plexsalud.plexsalud.appointment.services.AppointmentService;
import com.plexsalud.plexsalud.auth.services.JwtService;
import com.plexsalud.plexsalud.user.entities.Role;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Appointment", description = "Operations with appointments")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/appointment")
@RestController
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final JwtService jwtService;

    public AppointmentController(AppointmentService appointmentService, JwtService jwtService) {
        this.appointmentService = appointmentService;
        this.jwtService = jwtService;
    }

    @GetMapping("patient")
    public List<AppointmentResponse> getAllAppointmentsByDoctorSearchByPatient(HttpServletRequest request) {
        UUID uuid = jwtService.extractUuid(request);
        Role role = jwtService.extractRole(request);

        if (role != Role.PATIENT) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role Invalid");
        }

        return appointmentService.getAllAppointmentsByPatient(uuid);
    }

    @GetMapping("doctor")
    public List<AppointmentResponse> getAllAppointmentsByDoctor(HttpServletRequest request) {
        UUID uuid = jwtService.extractUuid(request);
        Role role = jwtService.extractRole(request);

        if (role != Role.DOCTOR) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role Invalid");
        }

        return appointmentService.getAllAppointmentsByDoctor(uuid);
    }

    @GetMapping("doctor-search-by-patient")
    public List<AppointmentResponse> getAllAppointmentsByDoctorSearchByPatient(HttpServletRequest request,
            @RequestParam("doctor") UUID uuid) {
        Role role = jwtService.extractRole(request);

        if (role != Role.PATIENT) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role Invalid");
        }

        return appointmentService.getAllAppointmentsByDoctorSearchByPatient(uuid);
    }

    @PostMapping
    public AppointmentResponse createAppointment(HttpServletRequest request,
            @RequestBody AppointmentDto appointmentDto) {

        UUID uuid = jwtService.extractUuid(request);
        Role role = jwtService.extractRole(request);

        if (role != Role.PATIENT) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role Invalid");
        }

        appointmentDto.setPatientUuid(uuid);

        return appointmentService.createAppointment(appointmentDto);
    }
}
