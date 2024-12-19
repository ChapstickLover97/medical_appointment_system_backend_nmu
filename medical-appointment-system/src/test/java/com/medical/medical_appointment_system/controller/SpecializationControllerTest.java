package com.medical.medical_appointment_system.controller;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.medical.medical_appointment_system.model.Specialization;
import com.medical.medical_appointment_system.repository.SpecializationRepository;

class SpecializationControllerTest {

    @Mock
    private SpecializationRepository specializationRepository;

    @InjectMocks
    private SpecializationController specializationController;

    private Specialization mockSpecialization;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create a mock specialization
        mockSpecialization = new Specialization();
        mockSpecialization.setId(1L);
        mockSpecialization.setName("Cardiology");
    }

    @Test
    void testGetAllSpecializations() {
        // Mock repository behavior
        List<Specialization> mockSpecializations = Arrays.asList(mockSpecialization);
        when(specializationRepository.findAll()).thenReturn(mockSpecializations);

        // Call the method
        ResponseEntity<List<Specialization>> response = specializationController.getAllSpecializations();

        // Assert the response
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(1);
        assertThat(response.getBody().get(0).getName()).isEqualTo("Cardiology");
        assertThat(response.getStatusCodeValue()).isEqualTo(200);

        // Verify repository interaction
        verify(specializationRepository, times(1)).findAll();
    }
}