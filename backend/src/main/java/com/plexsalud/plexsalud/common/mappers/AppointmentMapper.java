package com.plexsalud.plexsalud.common.mappers;

import com.plexsalud.plexsalud.appointment.domain.models.Appointment;
import com.plexsalud.plexsalud.appointment.infrastructure.persistance.entities.AppointmentEntity;

public class AppointmentMapper {

    public static Appointment toDomain(AppointmentEntity entity) {
        return new Appointment()
                .setUuid(entity.getUuid())
                .setDate(entity.getDate())
                .setStatus(entity.getStatus())
                .setDoctor(DoctorMapper.toDomain(entity.getDoctor()))
                .setPatient(PatientMapper.toDomain(entity.getPatient()));
    }

    public static AppointmentEntity toEntity(Appointment appointment) {
        AppointmentEntity entity = new AppointmentEntity();
        entity.setUuid(appointment.getUuid());
        entity.setDate(appointment.getDate());
        entity.setStatus(appointment.getStatus());
        entity.setDoctor(DoctorMapper.toEntity(appointment.getDoctor()));
        entity.setPatient(PatientMapper.toEntity(appointment.getPatient()));
        return entity;
    }
}
