package com.plexsalud.plexsalud.user.application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.plexsalud.plexsalud.user.infrastructure.persistance.entities.UserEntity;
import com.plexsalud.plexsalud.user.infrastructure.persistance.repositories.UserEntityRepository;


@Service
public class UserService {
    private final UserEntityRepository userRepository;

    public UserService(UserEntityRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getAllUsers() {
        List<UserEntity> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }

    public UserEntity getUser(UUID uuid) {
        Optional<UserEntity> userOptional = userRepository.findById(uuid);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new RuntimeException("User not found with UUID: " + uuid);
        }
    }
}
