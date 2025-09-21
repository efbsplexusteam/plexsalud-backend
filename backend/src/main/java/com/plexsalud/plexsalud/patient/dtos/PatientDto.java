package com.plexsalud.plexsalud.patient.dtos;

import java.util.UUID;

import lombok.Data;

@Data
public class PatientDto {
    private UUID userUuid;
    private String fullName;
}
