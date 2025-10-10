package com.plexsalud.plexsalud.common.mappers;

import com.plexsalud.plexsalud.doctor.domain.models.Doctor;
import com.plexsalud.plexsalud.doctor.infrastructure.persistance.entities.DoctorEntity;

public class DoctorMapper {

    public static Doctor toDomain(DoctorEntity entity) {
        if (entity == null)
            return null;

        return new Doctor()
                .setUuid(entity.getUuid())
                .setFullName(entity.getFullName())
                .setSpecialty(entity.getSpecialty())
                .setUser(UserMapper.toDomain(entity.getUser()));
                // .setAppointments(
                //         entity.getAppointments() != null
                //                 ? entity.getAppointments().stream()
                //                         .map(AppointmentMapper::toDomain)
                //                         .toList()
                //                 : null);
    }

    public static DoctorEntity toEntity(Doctor doctor) {
        if (doctor == null)
            return null;

        DoctorEntity entity = new DoctorEntity();
        entity.setUuid(doctor.getUuid());
        entity.setFullName(doctor.getFullName());
        entity.setSpecialty(doctor.getSpecialty());
        entity.setUser(UserMapper.toEntity(doctor.getUser()));
        // entity.setAppointments(
        //         doctor.getAppointments() != null
        //                 ? doctor.getAppointments().stream()
        //                         .map(AppointmentMapper::toEntity)
        //                         .toList()
        //                 : null);
        return entity;
    }
}
