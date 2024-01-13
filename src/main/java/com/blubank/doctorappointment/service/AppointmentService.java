package com.blubank.doctorappointment.service;

import com.blubank.doctorappointment.exception.ServiceException;
import com.blubank.doctorappointment.model.AppointmentFreeResponse;
import com.blubank.doctorappointment.model.Response;

import java.util.List;

public interface AppointmentService {

    Response addOpenTime(Long doctorId, String date, String start, String end) throws ServiceException;
    Response removeOpenTime(Long doctorId, String date, String start, String end) throws ServiceException;
    List<AppointmentFreeResponse> getDoctorAppointmentFree(Long doctorId)throws ServiceException;
}
