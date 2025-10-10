package com.plexsalud.plexsalud.appointment.infrastructure.persistance.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.plexsalud.plexsalud.appointment.infrastructure.persistance.entities.AppointmentEntity;
import com.plexsalud.plexsalud.doctor.infrastructure.persistance.entities.DoctorEntity;
import com.plexsalud.plexsalud.patient.infrastructure.persistance.entities.PatientEntity;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface AppointmentEntityRepository extends CrudRepository<AppointmentEntity, UUID> {
    List<AppointmentEntity> findAllAppointmentsByDoctor(DoctorEntity doctor);

    List<AppointmentEntity> findAllAppointmentsByPatient(PatientEntity patient);
}
