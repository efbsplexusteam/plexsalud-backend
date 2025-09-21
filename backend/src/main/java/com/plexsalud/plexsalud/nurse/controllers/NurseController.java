package com.plexsalud.plexsalud.nurse.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.plexsalud.plexsalud.auth.services.JwtService;
import com.plexsalud.plexsalud.nurse.dtos.NurseDto;
import com.plexsalud.plexsalud.nurse.responses.NurseResponse;
import com.plexsalud.plexsalud.nurse.services.NurseService;
import com.plexsalud.plexsalud.user.entities.Role;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Nurses", description = "Operations with nurses")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/nurses")
@RestController
public class NurseController {

    private final NurseService nurseService;
    private final JwtService jwtService;

    public NurseController(JwtService jwtService, NurseService nurseService) {
        this.jwtService = jwtService;
        this.nurseService = nurseService;
    }

    @PostMapping
    public ResponseEntity<NurseResponse> saveNurse(HttpServletRequest request,
            @RequestBody NurseDto nurseDto) {
        UUID uuid = jwtService.extractUuid(request);
        Role role = jwtService.extractRole(request);

        if (role != Role.NURSE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role Invalid");
        }

        nurseDto.setUserUuid(uuid);
        NurseResponse saved = nurseService.save(nurseDto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("self")
    public ResponseEntity<NurseResponse> findSelf(HttpServletRequest request) {
        UUID uuid = jwtService.extractUuid(request);

        NurseResponse nurseResponse = nurseService.findOneByUser(uuid);

        return ResponseEntity.ok(nurseResponse);
    }

    @GetMapping("uuid")
    public ResponseEntity<NurseResponse> findOne(@RequestParam("uuid") UUID uuid) {
        NurseResponse nurseResponse = nurseService.findOne(uuid);

        return ResponseEntity.ok(nurseResponse);
    }
}
