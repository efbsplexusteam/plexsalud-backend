package com.plexsalud.plexsalud.doctor.application.ports.in;

import com.plexsalud.plexsalud.doctor.domain.models.Doctor;

public interface CreateDoctorUseCase {
    Doctor save(Doctor doctor);
}
