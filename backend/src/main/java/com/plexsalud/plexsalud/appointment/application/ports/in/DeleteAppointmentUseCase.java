package com.plexsalud.plexsalud.appointment.application.ports.in;

import java.util.UUID;

public interface DeleteAppointmentUseCase {
    boolean deleteAppointment(UUID uuid);
}
