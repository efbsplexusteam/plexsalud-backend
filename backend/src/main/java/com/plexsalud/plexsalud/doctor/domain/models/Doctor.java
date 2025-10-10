package com.plexsalud.plexsalud.doctor.domain.models;

import java.util.List;
import java.util.UUID;

import com.plexsalud.plexsalud.appointment.domain.models.Appointment;
import com.plexsalud.plexsalud.user.domain.models.User;

public class Doctor {

        private UUID uuid;
        private String fullName;
        private String specialty;
        private User user;
        private List<Appointment> appointments;

        public UUID getUuid() {
                return uuid;
        }

        public String getFullName() {
                return fullName;
        }

        public String getSpecialty() {
                return specialty;
        }

        public User getUser() {
                return user;
        }

        public List<Appointment> getAppointments() {
                return appointments;
        }

        public Doctor setUuid(UUID uuid) {
                this.uuid = uuid;
                return this;
        }

        public Doctor setFullName(String fullName) {
                this.fullName = fullName;
                return this;
        }

        public Doctor setSpecialty(String specialty) {
                this.specialty = specialty;
                return this;
        }

        public Doctor setUser(User user) {
                this.user = user;
                return this;
        }

        public Doctor setAppointments(List<Appointment> appointments) {
                this.appointments = appointments;
                return this;
        }
}
