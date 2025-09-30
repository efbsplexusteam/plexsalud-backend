package com.plexsalud.plexsalud.user.application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.plexsalud.plexsalud.user.domain.entities.User;
import com.plexsalud.plexsalud.user.infrastructure.repositories.UserRepository;


@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
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
