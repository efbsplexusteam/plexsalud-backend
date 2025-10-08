package com.plexsalud.plexsalud.auth.application.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.plexsalud.plexsalud.auth.application.ports.in.AuthenticateUserUseCase;
import com.plexsalud.plexsalud.auth.application.ports.in.SignupUserUseCase;
import com.plexsalud.plexsalud.auth.infrastructure.dtos.LoginUserDto;
import com.plexsalud.plexsalud.auth.infrastructure.dtos.RegisterUserDto;
import com.plexsalud.plexsalud.auth.infrastructure.responses.RegisterResponse;
import com.plexsalud.plexsalud.user.domain.entities.User;
import com.plexsalud.plexsalud.user.infrastructure.repositories.UserRepository;

@Service
public class AuthenticationService implements SignupUserUseCase, AuthenticateUserUseCase {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponse signup(RegisterUserDto input) {
        User user = new User()
                .setRole(input.getRole())
                .setEmail(input.getEmail())
                .setPassword(passwordEncoder.encode(input.getPassword()));
        User userSaved = userRepository.save(user);
        return new RegisterResponse(userSaved.getRole());
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()));

        return userRepository.findByEmailAndRole(input.getEmail(), input.getRole())
                .orElseThrow();
    }
}
