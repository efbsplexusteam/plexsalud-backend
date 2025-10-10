package com.plexsalud.plexsalud.appointment.application.ports.in;

import com.plexsalud.plexsalud.appointment.domain.models.Appointment;

public interface CreateAppointmentUseCase {
    Appointment createAppointment(Appointment appointment);
}
