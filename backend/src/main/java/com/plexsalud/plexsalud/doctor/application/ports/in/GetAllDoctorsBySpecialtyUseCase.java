package com.plexsalud.plexsalud.doctor.application.ports.in;

import java.util.List;

import com.plexsalud.plexsalud.doctor.domain.models.Doctor;

public interface GetAllDoctorsBySpecialtyUseCase {
    List<Doctor> getDoctors(String specialty);
}
