package com.plexsalud.plexsalud.appointment.application.dtos;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class AppointmentDto {
    private OffsetDateTime date;

    private UUID doctorUuid;

    private UUID patientUuid;
}
