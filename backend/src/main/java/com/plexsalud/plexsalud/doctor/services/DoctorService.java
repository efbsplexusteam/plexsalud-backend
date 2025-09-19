package com.plexsalud.plexsalud.doctor.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.plexsalud.plexsalud.doctor.repositories.DoctorRepository;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    // public List<User> getAllUsers() {
    //     List<User> users = new ArrayList<>();

    //     userRepository.findAll().forEach(users::add);

    //     return users;
    // }

    // public User getUser(UUID uuid) {
    //     Optional<User> userOptional = userRepository.findById(uuid);
    //     if (userOptional.isPresent()) {
    //         return userOptional.get();
    //     } else {
    //         throw new RuntimeException("User not found with UUID: " + uuid);
    //     }
    // }
}
