package com.blubank.doctorappointment.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

public class AppointmentFreeResponse {

    private List<LocalTime> freeTimeRecord;
    private String date;

    public List<LocalTime> getFreeTimeRecord() {
        return freeTimeRecord;
    }

    public void setFreeTimeRecord(List<LocalTime> freeTimeRecord) {
        this.freeTimeRecord = freeTimeRecord;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
