package com.plexsalud.plexsalud.patient.domain.models;

import java.util.List;
import java.util.UUID;

import com.plexsalud.plexsalud.appointment.domain.models.Appointment;
import com.plexsalud.plexsalud.user.domain.models.User;

public class Patient {
        private UUID uuid;
        private String fullName;
        private User user;
        private List<Appointment> appointments;

        public UUID getUuid() {
                return uuid;
        }

        public Patient setUuid(UUID uuid) {
                this.uuid = uuid;
                return this;
        }

        public String getFullName() {
                return fullName;
        }

        public Patient setFullName(String fullName) {
                this.fullName = fullName;
                return this;
        }

        public User getUser() {
                return user;
        }

        public Patient setUser(User user) {
                this.user = user;
                return this;
        }

        public List<Appointment> getAppointments() {
                return appointments;
        }

        public Patient setAppointments(List<Appointment> appointments) {
                this.appointments = appointments;
                return this;
        }
}