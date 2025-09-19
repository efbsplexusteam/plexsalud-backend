package com.plexsalud.plexsalud.patient.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.plexsalud.plexsalud.user.entities.User;
import com.plexsalud.plexsalud.user.services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Enfermeros", description = "Operaciones con enfermeros")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/patients")
@RestController
public class PatientController {
    @Autowired
    private final UserService userService;

    public PatientController(UserService userService) {
        this.userService = userService;
    }

   

}
