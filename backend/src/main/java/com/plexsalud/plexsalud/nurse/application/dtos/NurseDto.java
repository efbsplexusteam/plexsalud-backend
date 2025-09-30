package com.plexsalud.plexsalud.nurse.application.dtos;

import java.util.UUID;

import lombok.Data;

@Data
public class NurseDto {
    private UUID userUuid;
    private String fullName;
}
