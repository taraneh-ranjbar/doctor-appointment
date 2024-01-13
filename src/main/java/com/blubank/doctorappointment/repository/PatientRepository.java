package com.blubank.doctorappointment.repository;

import com.blubank.doctorappointment.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<List<Patient>> findByPhoneNumber(String phone);
}
