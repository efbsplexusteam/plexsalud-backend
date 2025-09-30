package com.plexsalud.plexsalud.nurse.application.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.plexsalud.plexsalud.user.domain.entities.User;
import com.plexsalud.plexsalud.user.infrastructure.repositories.UserRepository;
import com.plexsalud.plexsalud.nurse.application.dtos.NurseDto;
import com.plexsalud.plexsalud.nurse.application.responses.NurseResponse;
import com.plexsalud.plexsalud.nurse.domain.entities.Nurse;
import com.plexsalud.plexsalud.nurse.domain.exceptions.NurseNotFoundException;
import com.plexsalud.plexsalud.nurse.infrastructure.repositories.NurseRepository;

@Service
public class NurseService {
    private final NurseRepository nurseRepository;
    private final UserRepository userRepository;

    public NurseService(NurseRepository nurseRepository, UserRepository userRepository) {
        this.nurseRepository = nurseRepository;
        this.userRepository = userRepository;
    }

    public NurseResponse save(NurseDto nurseDto) {
        Nurse nurse = new Nurse();

        User user = userRepository.findById(nurseDto.getUserUuid())
                .orElseThrow(() -> new RuntimeException("Nurse not found with uuid: " + nurseDto.getUserUuid()));

        nurse.setFullName(nurseDto.getFullName());
        nurse.setUser(user);

        nurse = nurseRepository.save(nurse);
        NurseResponse nurseResponse = new NurseResponse(nurse.getFullName());

        return nurseResponse;
    }

    public NurseResponse findOne(UUID uuid) {
        Nurse nurse = nurseRepository.findById(uuid)
                .orElseThrow(() -> new NurseNotFoundException(
                        "Nurse not found with uuid: " + uuid));

        return new NurseResponse(nurse.getFullName());
    }

    public NurseResponse findOneByUser(UUID uuid) {
        Nurse nurse = nurseRepository.findByUser(new User().setUuid(uuid))
                .orElseThrow(() -> new NurseNotFoundException(
                        "Nurse not found with uuid: " + uuid));

        return new NurseResponse(nurse.getFullName());
    }
}
