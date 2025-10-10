package com.plexsalud.plexsalud.appointment.application.ports.out;

import java.util.List;
import java.util.UUID;

import com.plexsalud.plexsalud.appointment.domain.models.Appointment;
import com.plexsalud.plexsalud.doctor.domain.models.Doctor;
import com.plexsalud.plexsalud.patient.domain.models.Patient;

public interface AppointmentRepositoryPort {
    Appointment save(Appointment appointment);

    void deleteById(UUID uuid);

    Boolean existsById(UUID uuid);

    List<Appointment> findAllAppointmentsByDoctor(Doctor doctor);

    List<Appointment> findAllAppointmentsByPatient(Patient patient);
}
