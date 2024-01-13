package com.blubank.doctorappointment.Controller;


import com.blubank.doctorappointment.exception.ServiceException;
import com.blubank.doctorappointment.model.AppointmentFreeResponse;
import com.blubank.doctorappointment.model.AppointmentRequest;
import com.blubank.doctorappointment.model.Response;
import com.blubank.doctorappointment.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping(value = "/create", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<Response> addOpenTimes(@RequestBody AppointmentRequest openTimes) throws ServiceException {
        Response response = appointmentService.addOpenTime(openTimes.getDoctorId(),openTimes.getDate(),openTimes.getStart(),openTimes.getEnd());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(value ="/remove", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<Response> removeOpenTime(@RequestBody AppointmentRequest openTimes) throws ServiceException {
        Response response = appointmentService.removeOpenTime(openTimes.getDoctorId(),openTimes.getDate(),openTimes.getStart(),openTimes.getEnd());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value ="/free/{doctorId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<AppointmentFreeResponse>> getDoctorAppointmentFree(@PathVariable Long doctorId) throws ServiceException{
       List<AppointmentFreeResponse> result = appointmentService.getDoctorAppointmentFree(doctorId);
       return ResponseEntity.ok().body(result);
    }
}
