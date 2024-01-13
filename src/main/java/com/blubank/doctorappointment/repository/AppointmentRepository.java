package com.blubank.doctorappointment.repository;

import com.blubank.doctorappointment.domain.Appointment;
import com.blubank.doctorappointment.domain.Doctor;
import com.blubank.doctorappointment.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<List<Appointment>> findByDoctorAndPatient(Doctor doctor, Patient patient);
}
