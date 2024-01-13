package com.blubank.doctorappointment.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientAppointmentResponse {

    private String patientFullName;
    private String patientPhone;
    private String doctorFullName;
    private String date;
    private String time;
}
