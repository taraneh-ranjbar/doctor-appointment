package com.blubank.doctorappointment.service.impl;

import com.blubank.doctorappointment.exception.ServiceException;
import com.blubank.doctorappointment.model.Response;
import com.blubank.doctorappointment.model.SpecialistRequest;
import com.blubank.doctorappointment.model.SpecialistModelAdapter;
import com.blubank.doctorappointment.repository.SpecialistRepository;
import com.blubank.doctorappointment.service.SpecialistService;
import com.blubank.doctorappointment.util.ConstantResponse;
import com.blubank.doctorappointment.util.Helper;
import org.springframework.stereotype.Service;


@Service
public class SpecialistServiceImpl implements SpecialistService {

    private final SpecialistRepository specialistRepository;
    private final Helper helper;

    public SpecialistServiceImpl(SpecialistRepository specialistRepository, Helper helper) {
        this.specialistRepository = specialistRepository;
        this.helper = helper;
    }

    @Override
    public Response create(SpecialistRequest specialistRequest) throws ServiceException {
        SpecialistModelAdapter adapter = new SpecialistModelAdapter(specialistRequest);
        specialistRepository.save(adapter);

        return helper.fillResponse(ConstantResponse.OK,ConstantResponse.SUCCESSFUL);
    }
}
