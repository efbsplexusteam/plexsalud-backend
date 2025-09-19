package com.plexsalud.plexsalud.patient.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.plexsalud.plexsalud.user.entities.User;
import com.plexsalud.plexsalud.user.repositories.UserRepository;


@Service
public class PatientService {
    private final UserRepository userRepository;

    public PatientService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }

    public User getUser(UUID uuid) {
        Optional<User> userOptional = userRepository.findById(uuid);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new RuntimeException("User not found with UUID: " + uuid);
        }
    }
}
