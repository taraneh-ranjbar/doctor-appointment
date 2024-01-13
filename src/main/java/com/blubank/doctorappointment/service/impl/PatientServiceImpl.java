package com.blubank.doctorappointment.service.impl;

import com.blubank.doctorappointment.domain.Appointment;
import com.blubank.doctorappointment.domain.Availabitity;
import com.blubank.doctorappointment.domain.Doctor;
import com.blubank.doctorappointment.domain.Patient;
import com.blubank.doctorappointment.exception.ServiceException;
import com.blubank.doctorappointment.model.AppointmentFreeResponse;
import com.blubank.doctorappointment.model.BookAppointmentRequest;
import com.blubank.doctorappointment.model.PatientAppointmentResponse;
import com.blubank.doctorappointment.model.Response;
import com.blubank.doctorappointment.repository.AppointmentRepository;
import com.blubank.doctorappointment.repository.DoctorRepository;
import com.blubank.doctorappointment.repository.PatientRepository;
import com.blubank.doctorappointment.service.PatientService;
import com.blubank.doctorappointment.util.ConstantResponse;
import com.blubank.doctorappointment.util.Helper;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final Helper helper;

    public PatientServiceImpl(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, PatientRepository patientRepository, Helper helper) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.helper = helper;
    }

    //Patients can view a doctor open appointment by date
    @Override
    public AppointmentFreeResponse getOpenAppointments(String date, Long doctorId) throws ServiceException {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ServiceException("doctor not exist", HttpStatus.SC_NOT_FOUND));
        AppointmentFreeResponse response = new AppointmentFreeResponse();
        Optional<Appointment> availability = doctor.getAvailabilities().stream()
                .filter(av -> av.getDate().equals(LocalDate.parse(date)))
                .findFirst();

        if(!availability.isPresent()){
            return response;
        }
            if(!availability.get().getPeriods().stream().filter(av -> !av.isTaken()).findFirst().get().isTaken()){
                List<LocalTime> freeTimes = new ArrayList<>();
                for (Availabitity time : availability.get().getPeriods()) {
                    freeTimes.add(time.getPeriodTimes());
                }
                response.setDate(String.valueOf(availability.get().getDate()));
                response.setFreeTimeRecord(freeTimes);
            }
       return response;
    }

    @Override
    public Response bookAppointment(BookAppointmentRequest bookAppointmentRequest) throws ServiceException {
        Doctor doctor = doctorRepository.findById(bookAppointmentRequest.getDoctorId())
                .orElseThrow(() -> new ServiceException("doctor not exist", HttpStatus.SC_NOT_FOUND));
        synchronized (PatientServiceImpl.class) {
            Patient patient = new Patient();
            patient.setFullName(bookAppointmentRequest.getFullName());
            patient.setPhoneNumber(bookAppointmentRequest.getPhone());
            patient.setDoctors(doctor);
            patientRepository.save(patient);

            Optional<Appointment> appointment = doctor.getAvailabilities().stream().
                    filter(av -> av.getDate().equals(LocalDate.parse(bookAppointmentRequest.getDate()))).findFirst();

            if(appointment.isEmpty()){
                throw new ServiceException("for the date "+ LocalDate.parse(bookAppointmentRequest.getDate()) +" , appointment not exist ",HttpStatus.SC_BAD_REQUEST);
            }
                if(!appointment.get().getPeriods().stream().filter(av -> !av.isTaken()).findFirst().get().isTaken()){
                    Optional<Availabitity> av = appointment.get().getPeriods().stream().filter
                                    (availabitity ->
                                            availabitity.getPeriodTimes().equals
                                            (LocalTime.parse(bookAppointmentRequest.getTimeRequest())) & !availabitity.isTaken()).findFirst();
                    if(!av.isPresent()){
                        throw new ServiceException("Time request not free. please chose another time",HttpStatus.SC_CONFLICT);
                    }
                    av.get().setTaken(true);
                    appointment.get().setPatient(patient);
                    doctor.getAvailabilities().add(appointment.get());
                    doctorRepository.save(doctor);
                }
        }
        return helper.fillResponse(ConstantResponse.OK, ConstantResponse.SUCCESSFUL);
    }

    @Override
    public List<PatientAppointmentResponse> getPatientAppointments(String phone) throws ServiceException{
        Optional<List<Patient>> patients =patientRepository.findByPhoneNumber(phone);
        if(patients.isEmpty()){
            throw new ServiceException("the patient with phone "+ phone+ " not exist",HttpStatus.SC_NOT_FOUND);
        }
        List<PatientAppointmentResponse> response = new ArrayList<>();
        for(Patient patient : patients.get()){
            Optional<List<Appointment>> appointments = appointmentRepository.findByDoctorAndPatient(patient.getDoctors(),patient);
            if(!appointments.isPresent()){
                return response;
            }
            for(Appointment appointment :appointments.get()){
                Optional<Availabitity> takenApp = appointment.getPeriods().stream().filter(availabitity -> !availabitity.isTaken()).findFirst();
                PatientAppointmentResponse result = new PatientAppointmentResponse();
                if(takenApp.get().isTaken()) {
                    result.setTime(String.valueOf(takenApp.get().getPeriodTimes()));
                    result.setDate(String.valueOf(appointment.getDate()));
                    result.setDoctorFullName(appointment.getDoctor().getFullName());
                }
                response.add(result);
            }
        }
        return response;
    }

}
