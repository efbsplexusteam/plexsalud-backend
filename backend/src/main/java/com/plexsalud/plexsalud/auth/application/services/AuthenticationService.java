package com.plexsalud.plexsalud.auth.application.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.plexsalud.plexsalud.auth.application.ports.in.AuthenticateUserUseCase;
import com.plexsalud.plexsalud.auth.application.ports.in.SignupUserUseCase;
import com.plexsalud.plexsalud.user.domain.models.User;
import com.plexsalud.plexsalud.user.infrastructure.persistance.entities.UserEntity;
import com.plexsalud.plexsalud.user.infrastructure.persistance.repositories.UserEntityRepository;

@Service
public class AuthenticationService implements SignupUserUseCase, AuthenticateUserUseCase {
    private final UserEntityRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserEntityRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(User user) {
        UserEntity userEntity = new UserEntity()
                .setRole(user.role())
                .setEmail(user.email())
                .setPassword(passwordEncoder.encode(user.password()));
        UserEntity userSaved = userRepository.save(userEntity);
        return new User(userSaved.getUuid(), userSaved.getUsername(), userSaved.getPassword(), userSaved.getRole());
    }

    public User authenticate(User user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.email(),
                        user.password()));

        return userRepository.findByEmailAndRole(user.email(), user.role())
                .map(u -> new User(u.getUuid(), u.getUsername(), u.getPassword(), u.getRole()))
                .orElseThrow();
    }
}
