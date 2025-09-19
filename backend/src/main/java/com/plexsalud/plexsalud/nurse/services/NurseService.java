package com.plexsalud.plexsalud.nurse.services;

import org.springframework.stereotype.Service;

import com.plexsalud.plexsalud.nurse.repositories.NurseRepository;

@Service
public class NurseService {
    private final NurseRepository nurseRepository;

    public NurseService(NurseRepository nurseRepository) {
        this.nurseRepository = nurseRepository;
    }
}
