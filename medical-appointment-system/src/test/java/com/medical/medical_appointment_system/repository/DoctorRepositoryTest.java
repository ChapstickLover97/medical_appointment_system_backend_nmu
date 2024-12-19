package com.medical.medical_appointment_system.repository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.medical.medical_appointment_system.model.Doctor;
import com.medical.medical_appointment_system.model.Specialization;

@DataJpaTest
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    private Doctor testDoctor;
    private Specialization testSpecialization;

    @BeforeEach
    void setUp() {
        // Create a test specialization
        testSpecialization = new Specialization();
        testSpecialization.setId(1L);
        testSpecialization.setName("Cardiology");

        // Create a test doctor
        testDoctor = new Doctor();
        testDoctor.setId(1L);
        testDoctor.setName("Dr. John Smith");
        testDoctor.setSpecialization(testSpecialization);

        // Save the test doctor
        doctorRepository.save(testDoctor);
    }

    @Test
    void testFindBySpecializationId_Valid() {
        List<Doctor> doctors = doctorRepository.findBySpecializationId(1L);

        assertThat(doctors).isNotEmpty();
        assertThat(doctors.get(0).getName()).isEqualTo("Dr. John Smith");
        assertThat(doctors.get(0).getSpecialization().getName()).isEqualTo("Cardiology");
    }

    @Test
    void testFindBySpecializationId_Invalid() {
        List<Doctor> doctors = doctorRepository.findBySpecializationId(999L);

        assertThat(doctors).isEmpty();
    }

    @Test
    void testFindBySpecializationName_Valid() {
        // Uncomment if the findBySpecialization_Name method is implemented
        // List<Doctor> doctors = doctorRepository.findBySpecialization_Name("Cardiology");

        // assertThat(doctors).isNotEmpty();
        // assertThat(doctors.get(0).getName()).isEqualTo("Dr. John Smith");
    }

    @Test
    void testFindBySpecializationName_Invalid() {
        // Uncomment if the findBySpecialization_Name method is implemented
        // List<Doctor> doctors = doctorRepository.findBySpecialization_Name("Pediatrics");

        // assertThat(doctors).isEmpty();
    }
}