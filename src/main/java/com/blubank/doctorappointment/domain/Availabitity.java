package com.blubank.doctorappointment.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalTime;


@Embeddable
public class Availabitity implements Serializable {

    @Column(name = "taken")
    private boolean taken = false;

    @Column(name = "periodTimes")
    private LocalTime periodTimes ;

    public Availabitity(LocalTime periodTimes) {;
        this.periodTimes = periodTimes;
    }

    public Availabitity() {

    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public LocalTime getPeriodTimes() {
        return periodTimes;
    }

    public void setPeriodTimes(LocalTime periodTimes) {
        this.periodTimes = periodTimes;
    }
}
