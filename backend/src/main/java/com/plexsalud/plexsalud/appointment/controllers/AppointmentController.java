package com.plexsalud.plexsalud.appointment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plexsalud.plexsalud.user.services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Enfermeros", description = "Operaciones con enfermeros")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/patients")
@RestController
public class AppointmentController {
    @Autowired
    private final UserService userService;

    public AppointmentController(UserService userService) {
        this.userService = userService;
    }

   

}
