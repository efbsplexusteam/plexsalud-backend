package com.plexsalud.plexsalud.appointment.application.ports.in;

import java.util.List;

import com.plexsalud.plexsalud.appointment.domain.models.Appointment;
import com.plexsalud.plexsalud.patient.domain.models.Patient;

public interface GetAllAppointmentsByPatientUseCase {
    List<Appointment> getAllAppointmentsByPatient(Patient patient);
}
