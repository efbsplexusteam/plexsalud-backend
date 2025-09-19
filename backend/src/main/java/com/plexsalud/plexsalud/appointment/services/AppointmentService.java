package com.plexsalud.plexsalud.appointment.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.plexsalud.plexsalud.appointment.entities.Appointment;
import com.plexsalud.plexsalud.appointment.repositories.AppointmentRepository;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public String saveAppointment() {
        return "Appointment Saved";
    }

    public String getAllAppointment() {
        return "All Appointment";
    }

    public String getOneAppointment(UUID uuid) {
        return "Appointment";

    }

    public String updateAppointment(UUID uuid) {
        return "Appointment Updated";

    }

    public String cancelAppointment(UUID uuid) {
        return "Appointment Deleted";
    }

}
