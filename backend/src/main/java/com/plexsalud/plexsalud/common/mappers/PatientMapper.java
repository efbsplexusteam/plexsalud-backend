package com.plexsalud.plexsalud.common.mappers;

import com.plexsalud.plexsalud.patient.domain.models.Patient;
import com.plexsalud.plexsalud.patient.infrastructure.persistance.entities.PatientEntity;

public class PatientMapper {

    public static Patient toDomain(PatientEntity entity) {
        if (entity == null)
            return null;

        return new Patient()
                .setUuid(entity.getUuid())
                .setFullName(entity.getFullName())
                .setUser(UserMapper.toDomain(entity.getUser()));
                // .setAppointments(
                //         entity.getAppointments() != null
                //                 ? entity.getAppointments().stream()
                //                         .map(AppointmentMapper::toDomain)
                //                         .toList()
                //                 : null);
    }

    public static PatientEntity toEntity(Patient patient) {
        if (patient == null)
            return null;

        PatientEntity entity = new PatientEntity();
        entity.setUuid(patient.getUuid());
        entity.setFullName(patient.getFullName());
        entity.setUser(UserMapper.toEntity(patient.getUser()));
        // entity.setAppointments(
        //         patient.getAppointments() != null
        //                 ? patient.getAppointments().stream()
        //                         .map(AppointmentMapper::toEntity)
        //                         .toList()
        //                 : null);
        return entity;
    }
}
