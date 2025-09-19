package com.plexsalud.plexsalud.appointment.services;

import org.springframework.stereotype.Service;

import com.plexsalud.plexsalud.appointment.repositories.AppointmentRepository;



@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

}
