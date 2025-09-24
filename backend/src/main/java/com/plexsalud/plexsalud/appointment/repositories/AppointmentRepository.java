package com.plexsalud.plexsalud.appointment.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.plexsalud.plexsalud.appointment.entities.Appointment;
import com.plexsalud.plexsalud.appointment.reponses.AppointmentResponse;
import com.plexsalud.plexsalud.doctor.entities.Doctor;
import com.plexsalud.plexsalud.patient.entities.Patient;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, UUID> {
    @Query("SELECT a.uuid, a.date FROM Appointment a WHERE a.doctor = :doctor")
    List<AppointmentResponse> findAllAppointmentsByDoctor(Doctor doctor);

    @Query("SELECT a.uuid, a.date FROM Appointment a WHERE a.patient = :patient")
    List<AppointmentResponse> findAllAppointmentsByPatient(Patient patient);
}
