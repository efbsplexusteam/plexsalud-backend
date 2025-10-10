package com.plexsalud.plexsalud.appointment.application.ports.in;

import java.util.List;

import com.plexsalud.plexsalud.appointment.domain.models.Appointment;
import com.plexsalud.plexsalud.doctor.domain.models.Doctor;

public interface GetAllAppointmentsByDoctorUseCase {
    List<Appointment> getAllAppointmentsByDoctor(Doctor doctor);
}
