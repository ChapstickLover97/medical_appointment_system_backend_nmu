package com.medical.medical_appointment_system.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
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

import com.medical.medical_appointment_system.model.Patient;
import com.medical.medical_appointment_system.repository.PatientRepository;

class PatientControllerTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientController patientController;

    private Patient mockPatient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create a mock patient
        mockPatient = new Patient();
        mockPatient.setId(1L);
        mockPatient.setFirstName("John");
        mockPatient.setLastName("Doe");
        mockPatient.setDateOfBirth(LocalDate.of(1990, 1, 1));
    }

    @Test
    void testCreatePatient() {
        when(patientRepository.save(any(Patient.class))).thenReturn(mockPatient);

        ResponseEntity<Patient> response = patientController.createPatient(mockPatient);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getFirstName()).isEqualTo("John");
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    void testGetAllPatients() {
        List<Patient> mockPatients = Arrays.asList(mockPatient);
        when(patientRepository.findAll()).thenReturn(mockPatients);

        ResponseEntity<List<Patient>> response = patientController.getAllPatients();

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(1);
        assertThat(response.getBody().get(0).getFirstName()).isEqualTo("John");
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void testGetPatientById() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(mockPatient));

        ResponseEntity<Patient> response = patientController.getPatientById(1L);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getFirstName()).isEqualTo("John");
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        verify(patientRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdatePatient() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(mockPatient));
        when(patientRepository.save(any(Patient.class))).thenReturn(mockPatient);

        Patient updatedPatient = new Patient();
        updatedPatient.setFirstName("Jane");
        updatedPatient.setLastName("Doe");
        updatedPatient.setDateOfBirth(LocalDate.of(1995, 5, 15));

        ResponseEntity<Patient> response = patientController.updatePatient(1L, updatedPatient);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getFirstName()).isEqualTo("Jane");
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        verify(patientRepository, times(1)).findById(1L);
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    void testDeletePatient() {
        doNothing().when(patientRepository).deleteById(1L);

        ResponseEntity<Void> response = patientController.deletePatient(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        verify(patientRepository, times(1)).deleteById(1L);
    }
}