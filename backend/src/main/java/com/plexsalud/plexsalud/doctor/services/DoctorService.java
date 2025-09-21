package com.plexsalud.plexsalud.doctor.services;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.plexsalud.plexsalud.doctor.dtos.DoctorDto;
import com.plexsalud.plexsalud.doctor.entities.Doctor;
import com.plexsalud.plexsalud.doctor.reponses.DoctorResponse;
import com.plexsalud.plexsalud.doctor.repositories.DoctorRepository;
import com.plexsalud.plexsalud.user.entities.User;
import com.plexsalud.plexsalud.user.repositories.UserRepository;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    public DoctorService(DoctorRepository doctorRepository, UserRepository userRepository) {
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
    }

    public DoctorResponse save(DoctorDto doctorDto) {
        Doctor doctor = new Doctor();

        // Recuperamos el User desde la BD
        User user = userRepository.findById(doctorDto.getUserUuid())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + doctorDto.getUserUuid()));

        doctor.setFullName(doctorDto.getFullName());
        doctor.setUser(user);

        doctor = doctorRepository.save(doctor);
        DoctorResponse doctorResponse = new DoctorResponse(doctor.getFullName());

        return doctorResponse;
    }

    public DoctorResponse findOne(UUID uuid) {
        Doctor doctor = doctorRepository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor no encontrado"));

        return new DoctorResponse(doctor.getFullName());
    }

    public DoctorResponse findOneByUser(UUID uuid) {
        Doctor doctor = doctorRepository.findByUser(new User().setUuid(uuid))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor no encontrado"));

        return new DoctorResponse(doctor.getFullName());
    }

    // public List<User> getAllUsers() {
    // List<User> users = new ArrayList<>();

    // userRepository.findAll().forEach(users::add);

    // return users;
    // }

    // public User getUser(UUID uuid) {
    // Optional<User> userOptional = userRepository.findById(uuid);
    // if (userOptional.isPresent()) {
    // return userOptional.get();
    // } else {
    // throw new RuntimeException("User not found with UUID: " + uuid);
    // }
    // }
}
