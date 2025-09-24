package com.plexsalud.plexsalud.auth.controllers;

import jakarta.servlet.http.Cookie;

import java.util.HashMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plexsalud.plexsalud.auth.dtos.LoginUserDto;
import com.plexsalud.plexsalud.auth.dtos.RegisterUserDto;
import com.plexsalud.plexsalud.auth.responses.LoginResponse;
import com.plexsalud.plexsalud.auth.responses.RegisterResponse;
import com.plexsalud.plexsalud.auth.services.AuthenticationService;
import com.plexsalud.plexsalud.auth.services.JwtService;
import com.plexsalud.plexsalud.user.entities.User;
import com.plexsalud.plexsalud.user.entities.Role;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping("/api/v1/auth")
@RestController
public class AuthenticationController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("signup")
    public RegisterResponse register(@RequestBody RegisterUserDto registerUserDto) {
        return authenticationService.signup(registerUserDto);
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        Role role = authenticatedUser.getRole();

        String jwtToken = jwtService.generateToken(authenticatedUser);

        // Generamos el refresh token con una vida útil más larga
        String refreshToken = jwtService.generateRefreshToken(authenticatedUser);

        // Enviamos el refresh token como una cookie
        ResponseCookie cookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(60 * 60 * 24 * 1)
                .build();

        LoginResponse loginResponse = new LoginResponse().setAccessToken(jwtToken).setRole(role)
                .setExpiresIn(jwtService.getExpirationTime());

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
        jwtService.getExpirationTime();

        HashMap<String, Object> map = jwtService.generateNewAccessToken(refreshToken);

        String jwtToken = (String) map.get("token");
        Role role = (Role) map.get("role");

        LoginResponse loginResponse = new LoginResponse().setAccessToken(jwtToken).setRole(role)
                .setExpiresIn(jwtService.getExpirationTime());

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
