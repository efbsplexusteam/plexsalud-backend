package com.plexsalud.plexsalud.auth.controllers;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.plexsalud.plexsalud.auth.responses.LoginResponse;
import com.plexsalud.plexsalud.auth.services.AuthenticationService;
import com.plexsalud.plexsalud.auth.services.JwtService;
import com.plexsalud.plexsalud.dtos.LoginUserDto;
import com.plexsalud.plexsalud.dtos.RegisterUserDto;
import com.plexsalud.plexsalud.user.entities.User;

@RequestMapping("/api/v1/public/auth")
@RestController
public class AuthenticationPublicRestController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationPublicRestController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> authenticate(@RequestParam String email, @RequestParam String password) {
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setPassword(password);
        loginUserDto.setEmail(email);

        try {
            // 👇 intentar autenticar
            User authenticatedUser = authenticationService.authenticate(loginUserDto);

            // generar token
            String jwtToken = jwtService.generateToken(authenticatedUser);

            // crear cookie
            ResponseCookie cookie = ResponseCookie.from("token", jwtToken)
                    .path("/")
                    .domain("plexsalud") // ojo: aquí debe coincidir con tu dominio real
                    .httpOnly(true)
                    .build();

            // redirigir al éxito
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .location(URI.create("http://plexsalud:3000/logued.php"))
                    .build();
        } catch (Exception e) {
            // 👇 en caso de fallo
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create("http://plexsalud:3000/login.php"))
                    .build();
        }

    }
}
