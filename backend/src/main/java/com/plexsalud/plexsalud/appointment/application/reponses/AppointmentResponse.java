package com.plexsalud.plexsalud.appointment.application.reponses;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AppointmentResponse(UUID uuid, OffsetDateTime date) {
}
