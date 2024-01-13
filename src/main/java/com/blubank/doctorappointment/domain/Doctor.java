package com.blubank.doctorappointment.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
public class Doctor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FULL_NAME",nullable = false)
    private String fullName;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> availabilities;

    @ManyToOne
    @JoinColumn(name = "SPECIALIST_ID", nullable = false)
    private Specialist specialist;

}
