package com.medical.medical_appointment_system.model;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import com.medical.medical_appointment_system.enums.AppointmentStatus;

class AppointmentTest {

    @Test
    void testNoArgsConstructor() {
        Appointment appointment = new Appointment();
        assertThat(appointment).isNotNull();
        assertThat(appointment.getId()).isNull();
        assertThat(appointment.getDoctor()).isNull();
        assertThat(appointment.getPatient()).isNull();
        assertThat(appointment.getDate()).isNull();
        assertThat(appointment.getTime()).isNull();
        assertThat(appointment.getStatus()).isEqualTo(AppointmentStatus.SCHEDULED);
    }

    @Test
    void testAllArgsConstructor() {
        Doctor doctor = new Doctor("Dr. John", null);
        Patient patient = new Patient("John", "Doe", LocalDate.of(1990, 1, 1), Patient.Status.ACTIVE);
        Appointment appointment = new Appointment(doctor, patient, LocalDate.of(2023, 12, 1), LocalTime.of(14, 0));

        assertThat(appointment.getDoctor()).isEqualTo(doctor);
        assertThat(appointment.getPatient()).isEqualTo(patient);
        assertThat(appointment.getDate()).isEqualTo(LocalDate.of(2023, 12, 1));
        assertThat(appointment.getTime()).isEqualTo(LocalTime.of(14, 0));
        assertThat(appointment.getStatus()).isEqualTo(AppointmentStatus.SCHEDULED);
    }

    @Test
    void testSettersAndGetters() {
        Appointment appointment = new Appointment();
        Doctor doctor = new Doctor("Dr. Alice", null);
        Patient patient = new Patient("Jane", "Smith", LocalDate.of(1995, 5, 15), Patient.Status.PENDING);

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDate(LocalDate.of(2024, 6, 15));
        appointment.setTime(LocalTime.of(10, 30));
        appointment.setStatus(AppointmentStatus.CANCELED);

        assertThat(appointment.getDoctor()).isEqualTo(doctor);
        assertThat(appointment.getPatient()).isEqualTo(patient);
        assertThat(appointment.getDate()).isEqualTo(LocalDate.of(2024, 6, 15));
        assertThat(appointment.getTime()).isEqualTo(LocalTime.of(10, 30));
        assertThat(appointment.getStatus()).isEqualTo(AppointmentStatus.CANCELED);
    }

    @Test
    void testToString() {
        Doctor doctor = new Doctor("Dr. Jane", null);
        Patient patient = new Patient("Alex", "Brown", LocalDate.of(1992, 11, 20), Patient.Status.INACTIVE);
        Appointment appointment = new Appointment(doctor, patient, LocalDate.of(2024, 1, 1), LocalTime.of(9, 0));

        String result = appointment.toString();
        assertThat(result).contains("Dr. Jane");
        assertThat(result).contains("Alex Brown");
        assertThat(result).contains("2024-01-01");
        assertThat(result).contains("09:00");
        assertThat(result).contains("SCHEDULED");
    }
}