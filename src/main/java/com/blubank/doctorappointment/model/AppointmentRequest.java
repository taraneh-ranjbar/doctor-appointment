package com.blubank.doctorappointment.model;

public class AppointmentRequest {

    private Long doctorId;
    private String date;
    private String start;
    private String end;

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public AppointmentRequest(Long doctorId, String date, String start, String end) {
        this.doctorId = doctorId;
        this.date = date;
        this.start = start;
        this.end = end;
    }
}
