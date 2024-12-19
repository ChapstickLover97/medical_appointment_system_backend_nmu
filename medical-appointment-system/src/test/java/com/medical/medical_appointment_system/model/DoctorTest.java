package com.medical.medical_appointment_system.model;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class DoctorTest {

    @Test
    void testNoArgsConstructor() {
        Doctor doctor = new Doctor();
        assertThat(doctor).isNotNull();
        assertThat(doctor.getId()).isNull();
        assertThat(doctor.getName()).isNull();
        assertThat(doctor.getSpecialization()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        Specialization specialization = new Specialization(1L, "Cardiology");
        Doctor doctor = new Doctor("Dr. John", specialization);

        assertThat(doctor.getName()).isEqualTo("Dr. John");
        assertThat(doctor.getSpecialization()).isEqualTo(specialization);
    }

    @Test
    void testSettersAndGetters() {
        Doctor doctor = new Doctor();
        Specialization specialization = new Specialization(2L, "Neurology");

        doctor.setId(1L);
        doctor.setName("Dr. Alice");
        doctor.setSpecialization(specialization);

        assertThat(doctor.getId()).isEqualTo(1L);
        assertThat(doctor.getName()).isEqualTo("Dr. Alice");
        assertThat(doctor.getSpecialization()).isEqualTo(specialization);
    }

    @Test
    void testToString() {
        Specialization specialization = new Specialization(3L, "Dermatology");
        Doctor doctor = new Doctor("Dr. Brown", specialization);

        String result = doctor.toString();
        assertThat(result).contains("Dr. Brown");
        assertThat(result).contains("Dermatology");
    }
}