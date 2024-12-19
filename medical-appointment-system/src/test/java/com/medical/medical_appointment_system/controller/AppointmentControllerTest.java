package com.medical.medical_appointment_system.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.medical.medical_appointment_system.enums.AppointmentStatus;
import com.medical.medical_appointment_system.model.Appointment;
import com.medical.medical_appointment_system.model.Doctor;
import com.medical.medical_appointment_system.model.Patient;
import com.medical.medical_appointment_system.repository.AppointmentRepository;
import com.medical.medical_appointment_system.repository.DoctorRepository;
import com.medical.medical_appointment_system.repository.PatientRepository;

class AppointmentControllerTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private AppointmentController appointmentController;

    private Appointment mockAppointment;
    private Doctor mockDoctor;
    private Patient mockPatient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create mock doctor, patient, and appointment
        mockDoctor = new Doctor();
        mockDoctor.setId(1L);

        mockPatient = new Patient();
        mockPatient.setId(1L);
        mockPatient.setDateOfBirth(LocalDate.of(1990, 1, 1));

        mockAppointment = new Appointment();
        mockAppointment.setId(1L);
        mockAppointment.setDoctor(mockDoctor);
        mockAppointment.setPatient(mockPatient);
        mockAppointment.setDate(LocalDate.of(2024, 12, 25));
        mockAppointment.setTime(LocalTime.of(14, 30));
        mockAppointment.setStatus(AppointmentStatus.SCHEDULED);
    }

    @Test
    void testGetAllAppointments() {
        when(appointmentRepository.findAll()).thenReturn(Arrays.asList(mockAppointment));

        List<Appointment> response = appointmentController.getAllAppointments();

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(1);
        assertThat(response.get(0).getDate()).isEqualTo(LocalDate.of(2024, 12, 25));
        verify(appointmentRepository, times(1)).findAll();
    }

    @Test
    void testGetAppointmentByIdAndDateOfBirth() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(mockAppointment));

        ResponseEntity<?> response = appointmentController.getAppointmentByIdAndDateOfBirth(1L, "1990-01-01");

        assertThat(response.getBody()).isNotNull();
        assertThat(((Appointment) response.getBody()).getId()).isEqualTo(1L);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        verify(appointmentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAppointmentByIdAndDateOfBirth_InvalidDate() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(mockAppointment));

        ResponseEntity<?> response = appointmentController.getAppointmentByIdAndDateOfBirth(1L, "1991-01-01");

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        verify(appointmentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAppointmentsByPatientId() {
        when(appointmentRepository.findByPatientIdAndPatientDateOfBirth(1L, LocalDate.of(1990, 1, 1)))
                .thenReturn(Arrays.asList(mockAppointment));

        ResponseEntity<?> response = appointmentController.getAppointmentsByPatientId(1L, "1990-01-01");

        assertThat(response.getBody()).isNotNull();
        assertThat(((List<?>) response.getBody()).size()).isEqualTo(1);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        verify(appointmentRepository, times(1))
                .findByPatientIdAndPatientDateOfBirth(1L, LocalDate.of(1990, 1, 1));
    }

    @Test
    void testCreateAppointment() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(mockDoctor));
        when(patientRepository.findById(1L)).thenReturn(Optional.of(mockPatient));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(mockAppointment);

        Map<String, Object> payload = Map.of(
                "doctorId", 1L,
                "patientId", 1L,
                "date", "2024-12-25",
                "time", "14:30"
        );

        ResponseEntity<Appointment> response = appointmentController.createAppointment(payload);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDoctor().getId()).isEqualTo(1L);
        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    void testUpdateAppointment() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(mockAppointment));
        when(doctorRepository.findById(2L)).thenReturn(Optional.of(new Doctor()));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(mockAppointment);

        Map<String, Object> payload = Map.of(
                "doctorId", 2L,
                "date", "2024-12-26",
                "time", "15:00"
        );

        ResponseEntity<Appointment> response = appointmentController.updateAppointment(1L, payload);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDate()).isEqualTo(LocalDate.of(2024, 12, 26));
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    void testDeleteAppointment() {
        when(appointmentRepository.existsById(1L)).thenReturn(true);
        doNothing().when(appointmentRepository).deleteById(1L);

        ResponseEntity<Void> response = appointmentController.deleteAppointment(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        verify(appointmentRepository, times(1)).deleteById(1L);
    }
}
