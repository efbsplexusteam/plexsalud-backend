package com.plexsalud.plexsalud.doctor.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.plexsalud.plexsalud.auth.services.JwtService;
import com.plexsalud.plexsalud.doctor.dtos.DoctorDto;
import com.plexsalud.plexsalud.doctor.entities.Doctor;
import com.plexsalud.plexsalud.doctor.reponses.DoctorResponse;
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

    @PostMapping("")

    public ResponseEntity<DoctorResponse> saveDoctor(HttpServletRequest request,
            @RequestBody DoctorDto doctorDto) {
        UUID uuid = jwtService.extractUuid(request);
        doctorDto.setUserUuid(uuid);
        DoctorResponse saved = doctorService.save(doctorDto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("self")
    public ResponseEntity<DoctorResponse> findSelf(HttpServletRequest request) {
        UUID uuid = jwtService.extractUuid(request);

        DoctorResponse doctorResponse = doctorService.findOneByUser(uuid);

        return ResponseEntity.ok(doctorResponse);
        // .map(ResponseEntity::ok) // ✅ 200 OK si existe
        // .orElseGet(() -> ResponseEntity.notFound().build()); // ❌ 404 si no existe
    }

    @GetMapping("uuid")
    public ResponseEntity<DoctorResponse> findOne(@RequestParam("uuid") UUID uuid) {
        DoctorResponse doctorResponse = doctorService.findOne(uuid);

        return ResponseEntity.ok(doctorResponse);

        // .map(ResponseEntity::ok) // ✅ 200 OK si existe
        // .orElseGet(() -> ResponseEntity.notFound().build()); // ❌ 404 si no existe
    }

}
