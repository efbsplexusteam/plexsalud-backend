package com.plexsalud.plexsalud.auth.application.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.plexsalud.plexsalud.auth.application.ports.in.AuthenticateUserUseCase;
import com.plexsalud.plexsalud.auth.application.ports.in.SignupUserUseCase;
import com.plexsalud.plexsalud.user.application.ports.out.UserRepositoryPort;
import com.plexsalud.plexsalud.user.domain.models.User;

@Service
public class AuthenticationService implements SignupUserUseCase, AuthenticateUserUseCase {
    private final UserRepositoryPort userRepositoryPort;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,

            UserRepositoryPort userRepositoryPort) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepositoryPort = userRepositoryPort;
    }

    public User signup(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepositoryPort.save(user);
    }

    public User authenticate(User user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()));

        return userRepositoryPort.findByEmailAndRole(user.getEmail(), user.getRole()).orElseThrow();
    }
}
