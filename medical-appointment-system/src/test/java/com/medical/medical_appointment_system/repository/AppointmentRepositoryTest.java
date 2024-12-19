package com.medical.medical_appointment_system.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.medical.medical_appointment_system.enums.AppointmentStatus;
import com.medical.medical_appointment_system.model.Appointment;
import com.medical.medical_appointment_system.model.Doctor;
import com.medical.medical_appointment_system.model.Patient;

@DataJpaTest
class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    private Appointment testAppointment;

    @BeforeEach
    void setUp() {
        // Create a test doctor
        Doctor testDoctor = new Doctor();
        testDoctor.setId(1L); // Ensure this matches your Doctor entity setup

        // Create a test patient
        Patient testPatient = new Patient();
        testPatient.setId(2L); // Ensure this matches your Patient entity setup

        // Create a test appointment
        testAppointment = new Appointment();
        testAppointment.setDoctor(testDoctor);
        testAppointment.setPatient(testPatient);
        testAppointment.setDate(LocalDate.of(2023, 12, 1));
        testAppointment.setTime(LocalTime.of(14, 0));
        testAppointment.setStatus(AppointmentStatus.SCHEDULED);

        // Save the test appointment
        appointmentRepository.save(testAppointment);
    }

    @Test
    void testFindByDoctorIdAndDate_Valid() {
        List<Appointment> appointments = appointmentRepository.findByDoctorIdAndDate(testAppointment.getDoctor().getId(), testAppointment.getDate());

        assertThat(appointments).isNotEmpty();
        assertThat(appointments.get(0).getDoctor().getId()).isEqualTo(testAppointment.getDoctor().getId());
        assertThat(appointments.get(0).getDate()).isEqualTo(testAppointment.getDate());
    }

    @Test
    void testFindByPatientIdAndDate_Valid() {
        List<Appointment> appointments = appointmentRepository.findByPatientIdAndDate(testAppointment.getPatient().getId(), testAppointment.getDate());

        assertThat(appointments).isNotEmpty();
        assertThat(appointments.get(0).getPatient().getId()).isEqualTo(testAppointment.getPatient().getId());
        assertThat(appointments.get(0).getDate()).isEqualTo(testAppointment.getDate());
    }

    @Test
    void testFindByDoctorIdAndDateAndTime_Valid() {
        List<Appointment> appointments = appointmentRepository.findByDoctorIdAndDateAndTime(testAppointment.getDoctor().getId(), testAppointment.getDate(), testAppointment.getTime());

        assertThat(appointments).isNotEmpty();
        assertThat(appointments.get(0).getDoctor().getId()).isEqualTo(testAppointment.getDoctor().getId());
        assertThat(appointments.get(0).getTime()).isEqualTo(testAppointment.getTime());
    }

    @Test
    void testFindByPatientIdAndDateAndTime_Valid() {
        List<Appointment> appointments = appointmentRepository.findByPatientIdAndDateAndTime(testAppointment.getPatient().getId(), testAppointment.getDate(), testAppointment.getTime());

        assertThat(appointments).isNotEmpty();
        assertThat(appointments.get(0).getPatient().getId()).isEqualTo(testAppointment.getPatient().getId());
        assertThat(appointments.get(0).getTime()).isEqualTo(testAppointment.getTime());
    }
}