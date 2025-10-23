package com.plexsalud.plexsalud.doctor.infrastructure.controllers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.RequestBody;

import com.plexsalud.plexsalud.auth.application.ports.in.ExtractRoleUseCase;
import com.plexsalud.plexsalud.auth.application.ports.in.ExtractUuidUseCase;
import com.plexsalud.plexsalud.doctor.application.dtos.DoctorDto;
import com.plexsalud.plexsalud.doctor.application.dtos.DoctorFullNameAndUuidDto;
import com.plexsalud.plexsalud.doctor.application.ports.in.CreateDoctorUseCase;
import com.plexsalud.plexsalud.doctor.application.ports.in.GetAllDoctorsBySpecialtyUseCase;
import com.plexsalud.plexsalud.doctor.application.ports.in.GetAllSpecialtiesUseCase;
import com.plexsalud.plexsalud.doctor.application.ports.in.GetDoctorByUserUseCase;
import com.plexsalud.plexsalud.doctor.application.ports.in.GetDoctorByUuidUseCase;
import com.plexsalud.plexsalud.doctor.application.reponses.DoctorResponse;
import com.plexsalud.plexsalud.doctor.domain.exceptions.DoctorNotFoundException;
import com.plexsalud.plexsalud.doctor.domain.models.Doctor;
import com.plexsalud.plexsalud.user.application.ports.in.GetUserByUuidUseCase;
import com.plexsalud.plexsalud.user.domain.models.Role;
import com.plexsalud.plexsalud.user.domain.models.User;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Doctors", description = "Operations with doctors")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/doctor")
@RestController
public class DoctorController {

    private final GetUserByUuidUseCase getUserByUuidUseCase;
    private final CreateDoctorUseCase createDoctorUseCase;
    private final GetAllDoctorsBySpecialtyUseCase getAllDoctorsBySpecialtyUseCase;
    private final GetAllSpecialtiesUseCase getAllSpecialtiesUseCase;
    private final GetDoctorByUserUseCase getDoctorByUserUseCase;
    private final GetDoctorByUuidUseCase getDoctorByUuidUseCase;
    private final ExtractUuidUseCase extractUuidUseCase;
    private final ExtractRoleUseCase extractRoleUseCase;

    public DoctorController(

            ExtractUuidUseCase extractUuidUseCase,
            ExtractRoleUseCase extractRoleUseCase,
            GetUserByUuidUseCase getUserByUuidUseCase,
            CreateDoctorUseCase createDoctorUseCase,
            GetAllDoctorsBySpecialtyUseCase getAllDoctorsBySpecialtyUseCase,
            GetAllSpecialtiesUseCase getAllSpecialtiesUseCase,
            GetDoctorByUserUseCase getDoctorByUserUseCase,
            GetDoctorByUuidUseCase getDoctorByUuidUseCase) {
        this.getUserByUuidUseCase = getUserByUuidUseCase;
        this.createDoctorUseCase = createDoctorUseCase;
        this.getAllDoctorsBySpecialtyUseCase = getAllDoctorsBySpecialtyUseCase;
        this.getAllSpecialtiesUseCase = getAllSpecialtiesUseCase;
        this.getDoctorByUserUseCase = getDoctorByUserUseCase;
        this.getDoctorByUuidUseCase = getDoctorByUuidUseCase;
        this.extractUuidUseCase = extractUuidUseCase;
        this.extractRoleUseCase = extractRoleUseCase;
    }

    @PostMapping
    public ResponseEntity<DoctorResponse> saveDoctor(HttpServletRequest request,
            @RequestBody DoctorDto doctorDto) {
        UUID uuid = extractUuidUseCase.extractUuid(request);
        Role role = extractRoleUseCase.extractRole(request);

        if (role != Role.DOCTOR) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role Invalid");
        }

        User user = getUserByUuidUseCase.getOne(uuid);

        Doctor doctor = new Doctor().setUser(user).setFullName(doctorDto.getFullName())
                .setSpecialty(doctorDto.getSpecialty());

        Doctor doctorSaved = createDoctorUseCase.save(doctor);
        return ResponseEntity.ok(new DoctorResponse(doctorSaved.getFullName(), doctorSaved.getSpecialty()));
    }

    @GetMapping("self")
    public ResponseEntity<DoctorResponse> findSelf(HttpServletRequest request) {
        UUID uuid = extractUuidUseCase.extractUuid(request);
        Doctor doctor = getDoctorByUserUseCase.getDoctor(new User().setUuid(uuid));
        
        if (doctor == null) {
            throw new DoctorNotFoundException("Doctor not found");
        }
        
        return ResponseEntity.ok(new DoctorResponse(doctor.getFullName(), doctor.getSpecialty()));
    }

    @GetMapping("uuid")
    public ResponseEntity<DoctorResponse> findOne(@RequestParam("uuid") UUID uuid) {
        Doctor doctor = getDoctorByUuidUseCase.getOneByUuid(uuid);
        return ResponseEntity.ok(new DoctorResponse(doctor.getFullName(), doctor.getSpecialty()));
    }

    @GetMapping("specialties")
    public List<String> findAllSpecialties() {
        return getAllSpecialtiesUseCase.getSpecialties();
    }

    @GetMapping("doctors-by-specialty")
    public List<DoctorFullNameAndUuidDto> findAllSpecialties(@RequestParam("specialty") String specialty) {
        return getAllDoctorsBySpecialtyUseCase.getDoctors(specialty.toLowerCase()).stream()
                .map(d -> new DoctorFullNameAndUuidDto(d.getFullName(), d.getUuid())).collect(Collectors.toList());
    }

}
