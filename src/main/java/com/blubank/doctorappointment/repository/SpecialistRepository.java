package com.blubank.doctorappointment.repository;

import com.blubank.doctorappointment.domain.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialistRepository extends JpaRepository<Specialist, Long> {
}
