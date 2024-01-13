package com.blubank.doctorappointment.service.impl;

import com.blubank.doctorappointment.domain.Appointment;
import com.blubank.doctorappointment.domain.Availabitity;
import com.blubank.doctorappointment.domain.Doctor;
import com.blubank.doctorappointment.exception.ServiceException;
import com.blubank.doctorappointment.model.AppointmentFreeResponse;
import com.blubank.doctorappointment.model.Response;
import com.blubank.doctorappointment.repository.DoctorRepository;
import com.blubank.doctorappointment.service.AppointmentService;
import com.blubank.doctorappointment.util.ConstantResponse;
import com.blubank.doctorappointment.util.Helper;
import com.hazelcast.core.HazelcastInstance;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;


@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AppointmentServiceImpl implements AppointmentService {

    private final Logger log = LogManager.getLogger(AppointmentServiceImpl.class);

    private final HazelcastInstance hazelcastInstance;
    private final DoctorRepository doctorRepository;
    private final Helper helper;

    public AppointmentServiceImpl(HazelcastInstance hazelcastInstance, DoctorRepository doctorRepository, Helper helper) {
        this.hazelcastInstance = hazelcastInstance;
        this.doctorRepository = doctorRepository;
        this.helper = helper;
    }

        private ConcurrentMap<Long,Doctor> retrieveMap() {
        return hazelcastInstance.getMap("map");
    }

    @Override
    public Response addOpenTime(Long doctorId, String date, String start, String end) throws ServiceException {
        log.info("start add open times by a doctor={}  with the parameters -> start={} , end={} , date={}",doctorId,start,end,date);
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->  new ServiceException("doctor not exist", HttpStatus.SC_NOT_FOUND));
        Appointment availability = doctor.getAvailabilities().stream()
                .filter(av -> av.getDate().equals(LocalDate.parse(date)))
                .findFirst()
                .orElseGet(() -> {
                    Appointment newAv = new Appointment();
                    newAv.setDoctor(doctor);
                    newAv.setDate(LocalDate.parse(date));
                    return newAv;
                });
        List<LocalTime> periodTimes = calculatePeriods(LocalTime.parse(start), LocalTime.parse(end));
         List<Availabitity> freeTimes = new ArrayList<>();
        for (LocalTime time : periodTimes) {
            Availabitity periods = new Availabitity(time);
            freeTimes.add(periods);
        }
        availability.setPeriods(freeTimes);
        doctor.getAvailabilities().add(availability);
        doctorRepository.save(doctor);
        log.info("finish add open times by a doctor={}  ",doctorId);
        retrieveMap().put(doctorId, doctor);

        return helper.fillResponse(ConstantResponse.OK,ConstantResponse.SUCCESSFUL);
    }

    private List<LocalTime> calculatePeriods(LocalTime start, LocalTime end) {
        List<LocalTime> periods = new ArrayList<>();
        LocalTime current = start;
        while (current.plusMinutes(30).isBefore(end) || current.plusMinutes(30).equals(end)) {
            periods.add(current);
            current = current.plusMinutes(30);
        }
        log.info("result of calculatePeriods is {} ",periods);
        return periods;
    }

    @Override
    public Response removeOpenTime(Long doctorId, String date, String start, String end) throws ServiceException{
        Doctor doctor = retrieveMap().get(doctorId);
        if (doctor == null) {
            doctor = doctorRepository.findById(doctorId).orElseThrow(() ->  new ServiceException("doctor not exist", HttpStatus.SC_NOT_FOUND));
        }
        Appointment availability = doctor.getAvailabilities().stream()
                .filter(av -> av.getDate().equals(LocalDate.parse(date)))
                .findFirst()
                .orElseThrow(() -> new ServiceException("doctor not exist", HttpStatus.SC_NOT_FOUND));
        List<LocalTime> periods = calculatePeriods(LocalTime.parse(start), LocalTime.parse(end));
        Optional<Availabitity> accessDel = availability.getPeriods().stream().filter(pr -> pr.isTaken() & pr.getPeriodTimes().equals(periods)).findFirst();
       if(accessDel.isPresent()){
           throw new ServiceException("Booked by the patient",HttpStatus.SC_NOT_ACCEPTABLE);
       }

        availability.getPeriods().removeAll(periods);
        doctorRepository.save(doctor);

        retrieveMap().put(doctorId, doctor);
        return helper.fillResponse(ConstantResponse.OK,ConstantResponse.SUCCESSFUL);
    }

    //Doctor can view 30 minutes free appointments
    @Override
    public List<AppointmentFreeResponse> getDoctorAppointmentFree(Long doctorId) throws ServiceException{
        Doctor doctor = retrieveMap().get(doctorId);
        if (doctor == null) {
            doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new ServiceException("doctor not exist", HttpStatus.SC_NOT_FOUND));
        }
        List<AppointmentFreeResponse> appointmentFreeResponse = new ArrayList<>();
        for (Appointment appointment : doctor.getAvailabilities()) {
            AppointmentFreeResponse model = new AppointmentFreeResponse();
            if(!appointment.getPeriods().stream().filter(av -> !av.isTaken()).findFirst().get().isTaken()){
                List<LocalTime> freeTimes = new ArrayList<>();
                for (Availabitity time : appointment.getPeriods()) {
                    freeTimes.add(time.getPeriodTimes());
                }
                model.setDate(String.valueOf(appointment.getDate()));
                model.setFreeTimeRecord(freeTimes);
            }
            appointmentFreeResponse.add(model);
        }
        return appointmentFreeResponse;
    }

}
