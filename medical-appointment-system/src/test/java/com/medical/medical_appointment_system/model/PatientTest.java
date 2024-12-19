package com.medical.medical_appointment_system.model;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class PatientTest {

    @Test
    void testNoArgsConstructor() {
        Patient patient = new Patient();
        assertThat(patient).isNotNull();
        assertThat(patient.getId()).isNull();
        assertThat(patient.getFirstName()).isNull();
        assertThat(patient.getLastName()).isNull();
        assertThat(patient.getDateOfBirth()).isNull();
        assertThat(patient.getStatus()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        Patient patient = new Patient("John", "Doe", LocalDate.of(1990, 1, 1), Patient.Status.ACTIVE);
        assertThat(patient.getFirstName()).isEqualTo("John");
        assertThat(patient.getLastName()).isEqualTo("Doe");
        assertThat(patient.getDateOfBirth()).isEqualTo(LocalDate.of(1990, 1, 1));
        assertThat(patient.getStatus()).isEqualTo(Patient.Status.ACTIVE);
    }

    @Test
    void testSettersAndGetters() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("Jane");
        patient.setLastName("Doe");
        patient.setDateOfBirth(LocalDate.of(1985, 5, 20));
        patient.setStatus(Patient.Status.PENDING);

        assertThat(patient.getId()).isEqualTo(1L);
        assertThat(patient.getFirstName()).isEqualTo("Jane");
        assertThat(patient.getLastName()).isEqualTo("Doe");
        assertThat(patient.getDateOfBirth()).isEqualTo(LocalDate.of(1985, 5, 20));
        assertThat(patient.getStatus()).isEqualTo(Patient.Status.PENDING);
    }

    @Test
    void testToString() {
        Patient patient = new Patient("Alice", "Smith", LocalDate.of(2000, 8, 15), Patient.Status.INACTIVE);
        String result = patient.toString();
        assertThat(result).contains("Alice");
        assertThat(result).contains("Smith");
        assertThat(result).contains("2000-08-15");
        assertThat(result).contains("INACTIVE");
    }
}