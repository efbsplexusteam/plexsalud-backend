package com.plexsalud.plexsalud.appointment.domain.models;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.plexsalud.plexsalud.doctor.domain.models.Doctor;
import com.plexsalud.plexsalud.patient.domain.models.Patient;

public class Appointment {
        private UUID uuid;
        private OffsetDateTime date;
        private String status;
        private Doctor doctor;
        private Patient patient;

        public UUID getUuid() {
                return uuid;
        }

        public OffsetDateTime getDate() {
                return date;
        }

        public String getStatus() {
                return status;
        }

        public Doctor getDoctor() {
                return doctor;
        }

        public Patient getPatient() {
                return patient;
        }

        public Appointment setUuid(UUID uuid) {
                this.uuid = uuid;
                return this;
        }

        public Appointment setDate(OffsetDateTime date) {
                this.date = date;
                return this;
        }

        public Appointment setStatus(String status) {
                this.status = status;
                return this;
        }

        public Appointment setDoctor(Doctor doctor) {
                this.doctor = doctor;
                return this;
        }

        public Appointment setPatient(Patient patient) {
                this.patient = patient;
                return this;
        }
}
