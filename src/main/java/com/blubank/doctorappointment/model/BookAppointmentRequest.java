package com.blubank.doctorappointment.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookAppointmentRequest {
    private Long doctorId;
    private String timeRequest;
    private String fullName;
    private String phone;
    private String date;
}
