package com.plexsalud.plexsalud.appointment.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.plexsalud.plexsalud.appointment.dtos.AppointmentDto;
import com.plexsalud.plexsalud.appointment.entities.Appointment;
import com.plexsalud.plexsalud.appointment.reponses.AppointmentResponse;
import com.plexsalud.plexsalud.appointment.repositories.AppointmentRepository;
import com.plexsalud.plexsalud.doctor.entities.Doctor;
import com.plexsalud.plexsalud.doctor.services.DoctorService;
import com.plexsalud.plexsalud.patient.entities.Patient;
import com.plexsalud.plexsalud.patient.services.PatientService;
import com.plexsalud.plexsalud.user.services.UserService;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorService doctorService;
    private final PatientService patientService;

    public AppointmentService(AppointmentRepository appointmentRepository, DoctorService doctorService,
            PatientService patientService, UserService userService) {
        this.appointmentRepository = appointmentRepository;
        this.doctorService = doctorService;
        this.patientService = patientService;

    }

    public AppointmentResponse createAppointment(AppointmentDto appointmentDto) {
        Doctor doctor = new Doctor();
        doctor.setUuid(appointmentDto.getDoctorUuid());
        Patient patient = patientService.findOneRaw(appointmentDto.getPatientUuid());

        Appointment appointment = new Appointment();

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDate(appointmentDto.getDate());
        appointment.setStatus("ACTIVE");

        Appointment appointmentSaved = appointmentRepository.save(appointment);

        return new AppointmentResponse(appointmentSaved.getUuid(), appointmentSaved.getDate());
    }

    public List<AppointmentResponse> getAllAppointmentsByPatient(UUID userUuid) {
        Patient patient = patientService.findOneRawByUser(userUuid);
        return appointmentRepository.findAllAppointmentsByPatient(patient);
    }

    public List<AppointmentResponse> getAllAppointmentsByDoctor(UUID userUuid) {
        Doctor doctor = doctorService.findOneRawByUser(userUuid);
        return appointmentRepository.findAllAppointmentsByDoctor(doctor);
    }

    public List<AppointmentResponse> getAllAppointmentsByDoctorSearchByPatient(UUID uuid) {
        Doctor doctor = new Doctor();
        doctor.setUuid(uuid);
        return appointmentRepository.findAllAppointmentsByDoctor(doctor);
    }

    public boolean deleteAppointment(UUID uuid) {
        try {
            if (!appointmentRepository.existsById(uuid)) {
                return false;
            }

            appointmentRepository.deleteById(uuid);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
