package com.plexsalud.plexsalud.appointment.entities;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.plexsalud.plexsalud.doctor.entities.Doctor;
import com.plexsalud.plexsalud.patient.entities.Patient;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "appointment")
@Entity
@Data
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID uuid;

    @Column(nullable = false)
    private OffsetDateTime date;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "doctor_uuid")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_uuid")
    private Patient patient;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
}
