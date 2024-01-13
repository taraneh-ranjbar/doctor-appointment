package com.blubank.doctorappointment.service;

import com.blubank.doctorappointment.exception.ServiceException;
import com.blubank.doctorappointment.model.AppointmentFreeResponse;
import com.blubank.doctorappointment.model.BookAppointmentRequest;
import com.blubank.doctorappointment.model.PatientAppointmentResponse;
import com.blubank.doctorappointment.model.Response;

import java.util.List;

public interface PatientService {
    AppointmentFreeResponse getOpenAppointments(String date, Long doctorId) throws ServiceException;
    Response bookAppointment(BookAppointmentRequest bookAppointmentRequest) throws ServiceException;
    List<PatientAppointmentResponse> getPatientAppointments(String phone) throws ServiceException;
}
