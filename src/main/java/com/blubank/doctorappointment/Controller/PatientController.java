package com.blubank.doctorappointment.Controller;

import com.blubank.doctorappointment.exception.ServiceException;
import com.blubank.doctorappointment.model.AppointmentFreeResponse;
import com.blubank.doctorappointment.model.BookAppointmentRequest;
import com.blubank.doctorappointment.model.PatientAppointmentResponse;
import com.blubank.doctorappointment.model.Response;
import com.blubank.doctorappointment.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/free/{date}/{doctorId}")
    public @ResponseBody ResponseEntity<AppointmentFreeResponse> getOpenAppointments(@PathVariable String date,@PathVariable Long doctorId) throws ServiceException {
        AppointmentFreeResponse result = patientService.getOpenAppointments(date,doctorId);
        return ResponseEntity.ok().body(result);

    }


    @PostMapping("/bookAppointment")
    public @ResponseBody ResponseEntity<Response> bookAppointment(@RequestBody BookAppointmentRequest BookAppointmentRequest) throws ServiceException {
        Response response = patientService.bookAppointment(BookAppointmentRequest);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/viewAppointment/{phone}")
    public @ResponseBody ResponseEntity<List<PatientAppointmentResponse>> getPatientAppointments(@PathVariable String phone) throws ServiceException{
        List<PatientAppointmentResponse> result = patientService.getPatientAppointments(phone);
        return ResponseEntity.ok().body(result);
    }

}
