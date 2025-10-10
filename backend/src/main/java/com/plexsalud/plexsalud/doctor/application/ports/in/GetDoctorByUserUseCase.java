package com.plexsalud.plexsalud.doctor.application.ports.in;

import com.plexsalud.plexsalud.doctor.domain.models.Doctor;
import com.plexsalud.plexsalud.user.domain.models.User;

public interface GetDoctorByUserUseCase {
    Doctor getDoctor(User user);
}
