package com.plexsalud.plexsalud.appointment.application.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.plexsalud.plexsalud.appointment.application.ports.in.CreateAppointmentUseCase;
import com.plexsalud.plexsalud.appointment.application.ports.in.DeleteAppointmentUseCase;
import com.plexsalud.plexsalud.appointment.application.ports.in.GetAllAppointmentsByDoctorSearchByPatientUseCase;
import com.plexsalud.plexsalud.appointment.application.ports.in.GetAllAppointmentsByDoctorUseCase;
import com.plexsalud.plexsalud.appointment.application.ports.in.GetAllAppointmentsByPatientUseCase;
import com.plexsalud.plexsalud.appointment.application.ports.out.AppointmentRepositoryPort;
import com.plexsalud.plexsalud.appointment.domain.models.Appointment;
import com.plexsalud.plexsalud.doctor.domain.models.Doctor;
import com.plexsalud.plexsalud.patient.domain.models.Patient;

@Service
public class AppointmentService implements CreateAppointmentUseCase, GetAllAppointmentsByDoctorUseCase,
        GetAllAppointmentsByPatientUseCase, GetAllAppointmentsByDoctorSearchByPatientUseCase, DeleteAppointmentUseCase {

    private final AppointmentRepositoryPort appointmentRepositoryPort;

    public AppointmentService(AppointmentRepositoryPort appointmentRepositoryPort) {
        this.appointmentRepositoryPort = appointmentRepositoryPort;
    }

    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepositoryPort.save(appointment);
    }

    public List<Appointment> getAllAppointmentsByPatient(Patient patient) {
        return appointmentRepositoryPort.findAllAppointmentsByPatient(patient);
    }

    public List<Appointment> getAllAppointmentsByDoctor(Doctor doctor) {
        return appointmentRepositoryPort.findAllAppointmentsByDoctor(doctor);
    }

    public List<Appointment> getAllAppointmentsByDoctorSearchByPatient(Doctor doctor) {
        return appointmentRepositoryPort.findAllAppointmentsByDoctor(doctor);
    }

    public boolean deleteAppointment(UUID uuid) {
        try {
            if (!appointmentRepositoryPort.existsById(uuid)) {
                return false;
            }

            appointmentRepositoryPort.deleteById(uuid);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
