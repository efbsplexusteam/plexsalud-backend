package com.plexsalud.plexsalud.appointment.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.plexsalud.plexsalud.appointment.entities.Appointment;
import com.plexsalud.plexsalud.user.entities.User;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, UUID> {
    Optional<Appointment> findByEmail(String email);
}
