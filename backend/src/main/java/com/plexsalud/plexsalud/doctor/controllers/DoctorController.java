package com.plexsalud.plexsalud.doctor.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plexsalud.plexsalud.auth.services.JwtService;
import com.plexsalud.plexsalud.doctor.services.DoctorService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Doctores", description = "Operaciones con doctores")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/doctor")
@RestController
public class DoctorController {
    @Autowired
    private final DoctorService doctorService;

    @Autowired
    private final JwtService jwtService;

    public DoctorController(DoctorService doctorService, JwtService jwtService) {
        this.doctorService = doctorService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public UUID saveDoctor(HttpServletRequest request) {
        UUID uuid = jwtService.extractUuid(request);
        return uuid;
    }

}
