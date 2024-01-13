package com.blubank.doctorappointment.service;

import com.blubank.doctorappointment.exception.ServiceException;
import com.blubank.doctorappointment.model.Response;
import com.blubank.doctorappointment.model.SpecialistRequest;

public interface SpecialistService {

    Response create(SpecialistRequest specialistRequest) throws ServiceException;
}
