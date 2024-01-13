package com.blubank.doctorappointment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Appointment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Doctor doctor;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "availability", joinColumns = @JoinColumn(name = "appointment_id"))
    private List<Availabitity> periods = new ArrayList<>();

    public Appointment() {

    }
}
