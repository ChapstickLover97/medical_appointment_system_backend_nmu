package com.medical.medical_appointment_system.repository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.medical.medical_appointment_system.model.Specialization;

@DataJpaTest
class SpecializationRepositoryTest {

    @Autowired
    private SpecializationRepository specializationRepository;

    private Specialization testSpecialization;

    @BeforeEach
    void setUp() {
        // Create a test specialization
        testSpecialization = new Specialization();
        testSpecialization.setName("Cardiology");

        // Save the test specialization to the in-memory database
        specializationRepository.save(testSpecialization);
    }

    @Test
    void testFindById_Valid() {
        Optional<Specialization> result = specializationRepository.findById(testSpecialization.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Cardiology");
    }

    @Test
    void testFindById_Invalid() {
        Optional<Specialization> result = specializationRepository.findById(999L);

        assertThat(result).isNotPresent();
    }

    @Test
    void testSaveAndRetrieveSpecialization() {
        Specialization newSpecialization = new Specialization();
        newSpecialization.setName("Neurology");

        Specialization savedSpecialization = specializationRepository.save(newSpecialization);

        assertThat(savedSpecialization.getId()).isNotNull();
        assertThat(savedSpecialization.getName()).isEqualTo("Neurology");
    }
}