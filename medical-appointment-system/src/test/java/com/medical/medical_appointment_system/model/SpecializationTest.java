package com.medical.medical_appointment_system.model;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class SpecializationTest {

    @Test
    void testNoArgsConstructor() {
        Specialization specialization = new Specialization();
        assertThat(specialization).isNotNull();
        assertThat(specialization.getId()).isNull();
        assertThat(specialization.getName()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        Specialization specialization = new Specialization(1L, "Pediatrics");
        assertThat(specialization.getId()).isEqualTo(1L);
        assertThat(specialization.getName()).isEqualTo("Pediatrics");
    }

    @Test
    void testSettersAndGetters() {
        Specialization specialization = new Specialization();
        specialization.setId(2L);
        specialization.setName("Orthopedics");

        assertThat(specialization.getId()).isEqualTo(2L);
        assertThat(specialization.getName()).isEqualTo("Orthopedics");
    }

    @Test
    void testToString() {
        Specialization specialization = new Specialization(3L, "Oncology");
        String result = specialization.toString();
        assertThat(result).contains("Oncology");
        assertThat(result).contains("3");
    }
}