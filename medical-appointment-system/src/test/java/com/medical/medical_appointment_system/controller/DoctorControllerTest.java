package com.medical.medical_appointment_system.controller;

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

import com.medical.medical_appointment_system.model.Doctor;
import com.medical.medical_appointment_system.model.Specialization;
import com.medical.medical_appointment_system.repository.DoctorRepository;
import com.medical.medical_appointment_system.repository.SpecializationRepository;

class DoctorControllerTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private SpecializationRepository specializationRepository;

    @InjectMocks
    private DoctorController doctorController;

    private Doctor mockDoctor;
    private Specialization mockSpecialization;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create mock specialization and doctor
        mockSpecialization = new Specialization();
        mockSpecialization.setId(1L);
        mockSpecialization.setName("Cardiology");

        mockDoctor = new Doctor();
        mockDoctor.setId(1L);
        mockDoctor.setName("Dr. John Smith");
        mockDoctor.setSpecialization(mockSpecialization);
    }

    @Test
    void testCreateDoctor() {
        when(specializationRepository.findById(1L)).thenReturn(Optional.of(mockSpecialization));
        when(doctorRepository.save(any(Doctor.class))).thenReturn(mockDoctor);

        Doctor newDoctor = new Doctor();
        newDoctor.setName("Dr. Jane Doe");
        newDoctor.setSpecialization(mockSpecialization);

        ResponseEntity<Doctor> response = doctorController.createDoctor(newDoctor);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Dr. Jane Doe");
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        verify(doctorRepository, times(1)).save(any(Doctor.class));
    }

    @Test
    void testGetAllDoctors() {
        when(doctorRepository.findAll()).thenReturn(Arrays.asList(mockDoctor));

        ResponseEntity<List<Doctor>> response = doctorController.getAllDoctors();

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(1);
        assertThat(response.getBody().get(0).getName()).isEqualTo("Dr. John Smith");
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        verify(doctorRepository, times(1)).findAll();
    }

    @Test
    void testGetDoctorsBySpecialization() {
        when(doctorRepository.findBySpecializationId(1L)).thenReturn(Arrays.asList(mockDoctor));

        ResponseEntity<List<Doctor>> response = doctorController.getDoctorsBySpecialization(1L);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(1);
        assertThat(response.getBody().get(0).getSpecialization().getName()).isEqualTo("Cardiology");
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        verify(doctorRepository, times(1)).findBySpecializationId(1L);
    }

    @Test
    void testGetDoctorById() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(mockDoctor));

        ResponseEntity<Doctor> response = doctorController.getDoctorById(1L);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getName()).isEqualTo("Dr. John Smith");
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        verify(doctorRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateDoctor() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(mockDoctor));
        when(specializationRepository.findById(1L)).thenReturn(Optional.of(mockSpecialization));
        when(doctorRepository.save(any(Doctor.class))).thenReturn(mockDoctor);

        Doctor updatedDoctor = new Doctor();
        updatedDoctor.setName("Dr. Updated Name");
        updatedDoctor.setSpecialization(mockSpecialization);

        ResponseEntity<Doctor> response = doctorController.updateDoctor(1L, updatedDoctor);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Dr. Updated Name");
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        verify(doctorRepository, times(1)).save(any(Doctor.class));
    }

    @Test
    void testDeleteDoctor() {
        doNothing().when(doctorRepository).deleteById(1L);

        ResponseEntity<Void> response = doctorController.deleteDoctor(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        verify(doctorRepository, times(1)).deleteById(1L);
    }
}
