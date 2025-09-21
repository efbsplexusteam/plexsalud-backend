package com.plexsalud.plexsalud.nurse.services;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.plexsalud.plexsalud.nurse.repositories.NurseRepository;
import com.plexsalud.plexsalud.nurse.responses.NurseResponse;
import com.plexsalud.plexsalud.user.entities.User;
import com.plexsalud.plexsalud.nurse.dtos.NurseDto;
import com.plexsalud.plexsalud.nurse.entities.Nurse;
import com.plexsalud.plexsalud.user.repositories.UserRepository;

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nurse not found with uuid:"));

        return new NurseResponse(nurse.getFullName());
    }

    public NurseResponse findOneByUser(UUID uuid) {
        Nurse nurse = nurseRepository.findByUser(new User().setUuid(uuid))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nurse not found with uuid:"));

        return new NurseResponse(nurse.getFullName());
    }
}
