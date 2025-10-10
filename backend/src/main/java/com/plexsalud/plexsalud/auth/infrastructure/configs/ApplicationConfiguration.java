package com.plexsalud.plexsalud.auth.infrastructure.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.plexsalud.plexsalud.common.mappers.UserMapper;
import com.plexsalud.plexsalud.user.application.ports.out.UserRepositoryPort;

@Configuration
public class ApplicationConfiguration {

    private final UserRepositoryPort userRepositoryPort;

    public ApplicationConfiguration(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;

    }

    @Bean
    UserDetailsService userDetailsService() {
        return username -> userRepositoryPort.findByEmail(username).map(u -> UserMapper.toEntity(u))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}
