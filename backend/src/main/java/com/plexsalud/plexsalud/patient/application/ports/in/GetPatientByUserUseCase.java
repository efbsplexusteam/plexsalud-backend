package com.plexsalud.plexsalud.patient.application.ports.in;

import com.plexsalud.plexsalud.patient.domain.models.Patient;
import com.plexsalud.plexsalud.user.domain.models.User;

public interface GetPatientByUserUseCase {
    Patient getByUser(User user);
}
