package com.plexsalud.plexsalud.appointment.infrastructure.persistance.repositories;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.plexsalud.plexsalud.appointment.application.ports.out.AppointmentRepositoryPort;
import com.plexsalud.plexsalud.appointment.domain.models.Appointment;
import com.plexsalud.plexsalud.appointment.infrastructure.persistance.entities.AppointmentEntity;
import com.plexsalud.plexsalud.common.mappers.AppointmentMapper;
import com.plexsalud.plexsalud.common.mappers.DoctorMapper;
import com.plexsalud.plexsalud.common.mappers.PatientMapper;
import com.plexsalud.plexsalud.doctor.domain.models.Doctor;
import com.plexsalud.plexsalud.patient.domain.models.Patient;

@Repository
public class AppointmentRepositoryAdapter implements AppointmentRepositoryPort {
    private final AppointmentEntityRepository appointmentRepository;

    public AppointmentRepositoryAdapter(AppointmentEntityRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment save(Appointment appointment) {
        AppointmentEntity appointmentEntity = appointmentRepository.save(AppointmentMapper.toEntity(appointment));
        return AppointmentMapper.toDomain(appointmentEntity);
    }

    public void deleteById(UUID uuid) {
        appointmentRepository.deleteById(uuid);
    };

    public Boolean existsById(UUID uuid) {
        return appointmentRepository.existsById(uuid);
    };

    public List<Appointment> findAllAppointmentsByDoctor(Doctor doctor) {
        return appointmentRepository.findAllAppointmentsByDoctor(DoctorMapper.toEntity(doctor)).stream()
                .map(a -> AppointmentMapper.toDomain(a)).collect(Collectors.toList());
    };

    public List<Appointment> findAllAppointmentsByPatient(Patient patient) {
        return appointmentRepository.findAllAppointmentsByPatient(PatientMapper.toEntity(patient)).stream()
                .map(a -> AppointmentMapper.toDomain(a)).collect(Collectors.toList());
    };
}
