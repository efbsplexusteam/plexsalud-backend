package com.plexsalud.plexsalud.appointment.infrastructure.controllers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.plexsalud.plexsalud.appointment.application.dtos.AppointmentDto;
import com.plexsalud.plexsalud.appointment.application.ports.in.CreateAppointmentUseCase;
import com.plexsalud.plexsalud.appointment.application.ports.in.DeleteAppointmentUseCase;
import com.plexsalud.plexsalud.appointment.application.ports.in.GetAllAppointmentsByDoctorSearchByPatientUseCase;
import com.plexsalud.plexsalud.appointment.application.ports.in.GetAllAppointmentsByDoctorUseCase;
import com.plexsalud.plexsalud.appointment.application.ports.in.GetAllAppointmentsByPatientUseCase;
import com.plexsalud.plexsalud.appointment.application.reponses.AppointmentResponse;
import com.plexsalud.plexsalud.appointment.domain.models.Appointment;
import com.plexsalud.plexsalud.auth.application.ports.in.ExtractRoleUseCase;
import com.plexsalud.plexsalud.auth.application.ports.in.ExtractUuidUseCase;
import com.plexsalud.plexsalud.doctor.application.ports.in.GetDoctorByUserUseCase;
import com.plexsalud.plexsalud.doctor.domain.models.Doctor;
import com.plexsalud.plexsalud.patient.application.ports.in.GetPatientByUserUseCase;
import com.plexsalud.plexsalud.patient.domain.models.Patient;
import com.plexsalud.plexsalud.user.domain.models.Role;
import com.plexsalud.plexsalud.user.domain.models.User;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Appointment", description = "Operations with appointments")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/appointment")
@RestController
public class AppointmentController {

    private final ExtractUuidUseCase extractUuidUseCase;
    private final ExtractRoleUseCase extractRoleUseCase;
    private final CreateAppointmentUseCase createAppointmentUseCase;
    private final DeleteAppointmentUseCase deleteAppointmentUseCase;
    private final GetAllAppointmentsByDoctorSearchByPatientUseCase getAllAppointmentsByDoctorSearchByPatientUseCase;
    private final GetAllAppointmentsByDoctorUseCase getAllAppointmentsByDoctorUseCase;
    private final GetAllAppointmentsByPatientUseCase getAllAppointmentsByPatientUseCase;
    private final GetPatientByUserUseCase getPatientByUserUseCase;
    private final GetDoctorByUserUseCase getDoctorByUserUseCase;

    public AppointmentController(

            ExtractUuidUseCase extractUuidUseCase,
            ExtractRoleUseCase extractRoleUseCase,
            CreateAppointmentUseCase createAppointmentUseCase,
            DeleteAppointmentUseCase deleteAppointmentUseCase,
            GetAllAppointmentsByDoctorSearchByPatientUseCase getAllAppointmentsByDoctorSearchByPatientUseCase,
            GetAllAppointmentsByDoctorUseCase getAllAppointmentsByDoctorUseCase,
            GetAllAppointmentsByPatientUseCase getAllAppointmentsByPatientUseCase,
            GetPatientByUserUseCase getPatientByUserUseCase,
            GetDoctorByUserUseCase getDoctorByUserUseCase) {

        this.extractUuidUseCase = extractUuidUseCase;
        this.extractRoleUseCase = extractRoleUseCase;
        this.createAppointmentUseCase = createAppointmentUseCase;
        this.deleteAppointmentUseCase = deleteAppointmentUseCase;
        this.getAllAppointmentsByDoctorSearchByPatientUseCase = getAllAppointmentsByDoctorSearchByPatientUseCase;
        this.getAllAppointmentsByDoctorUseCase = getAllAppointmentsByDoctorUseCase;
        this.getAllAppointmentsByPatientUseCase = getAllAppointmentsByPatientUseCase;
        this.getPatientByUserUseCase = getPatientByUserUseCase;
        this.getDoctorByUserUseCase = getDoctorByUserUseCase;
    }

    @GetMapping("patient")
    public List<AppointmentResponse> getAllAppointmentsByPatient(HttpServletRequest request) {
        UUID uuid = extractUuidUseCase.extractUuid(request);
        Role role = extractRoleUseCase.extractRole(request);

        if (role != Role.PATIENT) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role Invalid");
        }

        Patient patient = getPatientByUserUseCase.getByUser(new User().setUuid(uuid));

        return getAllAppointmentsByPatientUseCase.getAllAppointmentsByPatient(patient).stream()
                .map(a -> new AppointmentResponse(a.getUuid(), a.getDate())).collect(Collectors.toList());
    }

    @GetMapping("doctor")
    public List<AppointmentResponse> getAllAppointmentsByDoctor(HttpServletRequest request) {
        UUID uuid = extractUuidUseCase.extractUuid(request);
        Role role = extractRoleUseCase.extractRole(request);

        if (role != Role.DOCTOR) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role Invalid");
        }

        Doctor doctor = getDoctorByUserUseCase.getDoctor(new User().setUuid(uuid));

        return getAllAppointmentsByDoctorUseCase.getAllAppointmentsByDoctor(doctor).stream()
                .map(a -> new AppointmentResponse(a.getUuid(), a.getDate())).collect(Collectors.toList());
    }

    @GetMapping("doctor-search-by-patient")
    public List<AppointmentResponse> getAllAppointmentsByDoctorSearchByPatient(HttpServletRequest request,
            @RequestParam("doctor") UUID uuid) {
        Role role = extractRoleUseCase.extractRole(request);

        if (role != Role.PATIENT) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role Invalid");
        }

        Doctor doctor = getDoctorByUserUseCase.getDoctor(new User().setUuid(uuid));

        return getAllAppointmentsByDoctorSearchByPatientUseCase.getAllAppointmentsByDoctorSearchByPatient(doctor).stream()
                .map(a -> new AppointmentResponse(a.getUuid(), a.getDate())).collect(Collectors.toList());
    }

    @PostMapping
    public AppointmentResponse createAppointment(HttpServletRequest request,
            @RequestBody AppointmentDto appointmentDto) {

        UUID uuid = extractUuidUseCase.extractUuid(request);
        Role role = extractRoleUseCase.extractRole(request);

        if (role != Role.PATIENT) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role Invalid");
        }

        Patient patient = getPatientByUserUseCase.getByUser(new User().setUuid(uuid));
        Doctor doctor = new Doctor().setUuid(appointmentDto.getDoctorUuid());

        Appointment appointment = new Appointment().setDoctor(doctor).setPatient(patient)
                .setDate(appointmentDto.getDate()).setStatus("CREATED");

        Appointment appointmentCreated = createAppointmentUseCase.createAppointment(appointment);
        return new AppointmentResponse(appointmentCreated.getUuid(), appointmentCreated.getDate());
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> deleteAppointment(@PathVariable("uuid") UUID uuid) {
        boolean isDeleted = deleteAppointmentUseCase.deleteAppointment(uuid);

        if (isDeleted) {
            return ResponseEntity.ok("Appointment deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found");
        }
    }
}
