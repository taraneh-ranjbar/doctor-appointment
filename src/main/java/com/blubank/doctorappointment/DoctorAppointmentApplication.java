package com.blubank.doctorappointment;

import com.blubank.doctorappointment.domain.Appointment;
import com.blubank.doctorappointment.domain.Doctor;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdminServer
@SpringBootApplication
public class DoctorAppointmentApplication {


	public static void main(String[] args) {
		SpringApplication.run(DoctorAppointmentApplication.class, args);
	}


}
