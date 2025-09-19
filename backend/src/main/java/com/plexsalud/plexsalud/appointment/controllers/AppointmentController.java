package com.plexsalud.plexsalud.appointment.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.plexsalud.plexsalud.appointment.entities.Appointment;
import com.plexsalud.plexsalud.appointment.services.AppointmentService;
import com.plexsalud.plexsalud.doctor.entities.Doctor;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Enfermeros", description = "Operaciones con enfermeros")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/appointment")
@RestController
public class AppointmentController {
    @Autowired
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    public List<Appointment> getAppointmentsByDateRangeAndDoctor(LocalDate starDate, LocalDate endDate, Doctor doctor) {
        List<Appointment> appointments = new ArrayList<>();

        return appointments;
    }

    public List<Appointment> saveAppointment(LocalDate starDate, LocalDate endDate, Doctor doctor) {
        List<Appointment> appointments = new ArrayList<>();

        return appointments;
    }

    @PostMapping
    public String saveAppointment() {
        return appointmentService.saveAppointment();
    }

    @GetMapping("")
    public String getAllAppointment() {
        return appointmentService.saveAppointment();

    }

    @GetMapping("{uuid}")
    public String getOneAppointment(@RequestParam UUID uuid) {
        return appointmentService.saveAppointment();

    }

    @PatchMapping("{uuid}")
    public String updateAppointment(UUID uuid) {
        return appointmentService.updateAppointment(uuid);

    }

    @DeleteMapping("{uuid}")
    public String cancelAppointment(UUID uuid) {
        return appointmentService.cancelAppointment(uuid);

    }

}
