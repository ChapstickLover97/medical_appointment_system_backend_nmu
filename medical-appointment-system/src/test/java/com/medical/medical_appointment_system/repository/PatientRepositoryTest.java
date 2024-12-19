package com.medical.medical_appointment_system.repository;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.medical.medical_appointment_system.model.Patient;

// The repository is untestable in its current state due to missing configuration for an embedded database or misconfiguration of the `Patient` entity or repository setup.

@DataJpaTest // Sets up an in-memory database for testing
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    private Patient testPatient;

    @BeforeEach
    void setUp() {
        // Create a test patient
        testPatient = new Patient();
        testPatient.setFirstName("John");
        testPatient.setLastName("Doe");
        testPatient.setDateOfBirth(LocalDate.of(1990, 1, 1));

        // Save test patient to in-memory database
        patientRepository.save(testPatient);
    }

    @Test
    void testFindByFirstNameAndLastNameAndDateOfBirth_Valid() {
        // Execute query with valid data
        Optional<Patient> result = patientRepository.findByFirstNameAndLastNameAndDateOfBirth(
                "John", "Doe", LocalDate.of(1990, 1, 1)
        );

        // Assertions
        assertThat(result).isPresent();
        assertThat(result.get().getFirstName()).isEqualTo("John");
        assertThat(result.get().getLastName()).isEqualTo("Doe");
        assertThat(result.get().getDateOfBirth()).isEqualTo(LocalDate.of(1990, 1, 1));
    }

    @Test
    void testFindByFirstNameAndLastNameAndDateOfBirth_Invalid() {
        // Execute query with invalid data
        Optional<Patient> result = patientRepository.findByFirstNameAndLastNameAndDateOfBirth(
                "Jane", "Doe", LocalDate.of(1990, 1, 1)
        );

        // Assertions
        assertThat(result).isNotPresent();
    }
}