package com.plexsalud.plexsalud.auth.configs;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Value("${app.cors.allowed-origin}")
    private String allowedOrigin;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            AuthenticationProvider authenticationProvider) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // ⚠️ Deshabilitar CSRF para APIs REST, pero mantenerlo para formularios
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos (registro, login, recursos estáticos)
                        .requestMatchers("/auth/login-form", "/api/auth/login", "/api/auth/signup",
                                "/uploads/**", "/swagger-ui/**",
                                "/v3/api-docs/**", "/css/**", "/js/**", "/favicon.ico")
                        .permitAll()

                        // Endpoints REST deben usar JWT/Bearer
                        .requestMatchers("/api/v1/**").authenticated()

                        // requieren login por sesión/cookie
                        .requestMatchers("/api/v1/**").authenticated())
                // 👇 para que funcionen Bearer y cookies o sesiones
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(allowedOrigin));
        configuration.setAllowedMethods(List.of("GET", "POST"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
