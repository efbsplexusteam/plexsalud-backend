package com.plexsalud.plexsalud.auth.infrastructure.controllers;

import jakarta.servlet.http.Cookie;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plexsalud.plexsalud.auth.application.ports.in.AuthenticateUserUseCase;
import com.plexsalud.plexsalud.auth.application.ports.in.GenerateNewAccessToken;
import com.plexsalud.plexsalud.auth.application.ports.in.GenerateRefreshTokenUseCase;
import com.plexsalud.plexsalud.auth.application.ports.in.GenerateTokenUseCase;
import com.plexsalud.plexsalud.auth.application.ports.in.SignupUserUseCase;
import com.plexsalud.plexsalud.auth.infrastructure.dtos.LoginUserDto;
import com.plexsalud.plexsalud.auth.infrastructure.dtos.RegisterUserDto;
import com.plexsalud.plexsalud.auth.infrastructure.responses.LoginResponse;
import com.plexsalud.plexsalud.auth.infrastructure.responses.RegisterResponse;
import com.plexsalud.plexsalud.user.domain.models.Role;
import com.plexsalud.plexsalud.user.domain.models.User;
import com.plexsalud.plexsalud.user.infrastructure.persistance.entities.UserEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping("/api/v1/auth")
@RestController
public class AuthenticationController {

    private final SignupUserUseCase signupUserUseCase;
    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final GenerateTokenUseCase generateTokenUseCase;
    private final GenerateRefreshTokenUseCase generateRefreshTokenUseCase;
    private final GenerateNewAccessToken generateNewAccessToken;

    public AuthenticationController(

            SignupUserUseCase signupUserUseCase,
            AuthenticateUserUseCase authenticateUserUseCase,
            GenerateTokenUseCase generateTokenUseCase,
            GenerateRefreshTokenUseCase generateRefreshTokenUseCase,
            GenerateNewAccessToken generateNewAccessToken) {
        this.signupUserUseCase = signupUserUseCase;
        this.authenticateUserUseCase = authenticateUserUseCase;
        this.generateTokenUseCase = generateTokenUseCase;
        this.generateRefreshTokenUseCase = generateRefreshTokenUseCase;
        this.generateNewAccessToken = generateNewAccessToken;
    }

    @PostMapping("signup")
    public RegisterResponse register(@RequestBody RegisterUserDto registerUserDto) {
        User user = new User(new UUID(0, 0), registerUserDto.getEmail(), registerUserDto.getPassword(),
                registerUserDto.getRole());

        User userSaved = signupUserUseCase.signup(user);

        return new RegisterResponse(userSaved.role());
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User user = new User(new UUID(0, 0), loginUserDto.getEmail(), loginUserDto.getPassword(),
                loginUserDto.getRole());

        User authenticatedUser = authenticateUserUseCase.authenticate(user);
        // UserEntity authenticatedUser =
        Role role = authenticatedUser.role();

        String jwtToken = generateTokenUseCase.generateTokenUseCase(authenticatedUser);

        String refreshToken = generateRefreshTokenUseCase.generateRefreshTokenUseCase(authenticatedUser);

        ResponseCookie cookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(60 * 60 * 24 * 1)
                .build();

        LoginResponse loginResponse = new LoginResponse().setAccessToken(jwtToken).setRole(role);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString()) // Seteamos la cookie en la cabecera
                .body(loginResponse);
    }

    @GetMapping("logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie refreshTokenCookie = new Cookie("refresh_token", null);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0);

        response.addCookie(refreshTokenCookie);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("refresh")
    public ResponseEntity<LoginResponse> refreshToken(HttpServletRequest request) {
        String refreshToken = extractJwtFromCookies(request);

        HashMap<String, Object> map = generateNewAccessToken.generateNewAccessToken(refreshToken);

        String jwtToken = (String) map.get("token");
        Role role = (Role) map.get("role");

        LoginResponse loginResponse = new LoginResponse().setAccessToken(jwtToken).setRole(role);

        return ResponseEntity.ok(loginResponse);
    }

    private String extractJwtFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refresh_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
