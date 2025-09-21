package com.plexsalud.plexsalud.doctor.dtos;

import java.util.UUID;

import lombok.Data;
@Data
public class DoctorDto {

    private String fullName;
    private UUID userUuid;

    public void setFullName(String fullName) {
        this.fullName = (fullName != null) ? fullName.toLowerCase() : null;
    }
}