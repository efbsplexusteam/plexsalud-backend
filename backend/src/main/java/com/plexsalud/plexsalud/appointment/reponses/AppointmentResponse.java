package com.plexsalud.plexsalud.appointment.reponses;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AppointmentResponse(UUID uuid, OffsetDateTime date) {
}
