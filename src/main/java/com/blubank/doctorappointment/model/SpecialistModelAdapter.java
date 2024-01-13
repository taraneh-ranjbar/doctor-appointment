package com.blubank.doctorappointment.model;

import com.blubank.doctorappointment.domain.Specialist;

public class SpecialistModelAdapter extends Specialist {

    private SpecialistRequest specialistMode;
    public SpecialistModelAdapter(SpecialistRequest specialistMode) {
        this.specialistMode = specialistMode;
    }

    @Override
    public String getCode() {
        return specialistMode.getCode();
    }

    @Override
    public String getName() {
        return specialistMode.getName();
    }
}
