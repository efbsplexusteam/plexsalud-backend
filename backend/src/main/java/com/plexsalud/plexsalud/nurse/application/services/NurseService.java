package com.plexsalud.plexsalud.nurse.application.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.plexsalud.plexsalud.user.infrastructure.persistance.entities.UserEntity;
import com.plexsalud.plexsalud.user.infrastructure.persistance.repositories.UserEntityRepository;
import com.plexsalud.plexsalud.nurse.application.dtos.NurseDto;
import com.plexsalud.plexsalud.nurse.application.responses.NurseResponse;
import com.plexsalud.plexsalud.nurse.domain.exceptions.NurseNotFoundException;
import com.plexsalud.plexsalud.nurse.infrastructure.persistance.entities.NurseEntity;
import com.plexsalud.plexsalud.nurse.infrastructure.persistance.repositories.NurseEntityRepository;

@Service
public class NurseService {
    private final NurseEntityRepository nurseRepository;
    private final UserEntityRepository userRepository;

    public NurseService(NurseEntityRepository nurseRepository, UserEntityRepository userRepository) {
        this.nurseRepository = nurseRepository;
        this.userRepository = userRepository;
    }

    public NurseResponse save(NurseDto nurseDto) {
        NurseEntity nurse = new NurseEntity();

        UserEntity user = userRepository.findById(nurseDto.getUserUuid())
                .orElseThrow(() -> new RuntimeException("Nurse not found with uuid: " + nurseDto.getUserUuid()));

        nurse.setFullName(nurseDto.getFullName());
        nurse.setUser(user);

        nurse = nurseRepository.save(nurse);
        NurseResponse nurseResponse = new NurseResponse(nurse.getFullName());

        return nurseResponse;
    }

    public NurseResponse findOne(UUID uuid) {
        NurseEntity nurse = nurseRepository.findById(uuid)
                .orElseThrow(() -> new NurseNotFoundException(
                        "Nurse not found with uuid: " + uuid));

        return new NurseResponse(nurse.getFullName());
    }

    public NurseResponse findOneByUser(UUID uuid) {
        NurseEntity nurse = nurseRepository.findByUser(new UserEntity().setUuid(uuid))
                .orElseThrow(() -> new NurseNotFoundException(
                        "Nurse not found with uuid: " + uuid));

        return new NurseResponse(nurse.getFullName());
    }
}
