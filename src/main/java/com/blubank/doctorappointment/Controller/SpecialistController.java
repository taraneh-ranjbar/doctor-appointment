package com.blubank.doctorappointment.Controller;

import com.blubank.doctorappointment.exception.ServiceException;
import com.blubank.doctorappointment.model.Response;
import com.blubank.doctorappointment.model.SpecialistRequest;
import com.blubank.doctorappointment.service.SpecialistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/specialist")
public class SpecialistController {

    private final SpecialistService specialistService;

    public SpecialistController(SpecialistService specialistService) {
        this.specialistService = specialistService;
    }


    @PostMapping("/save")
    public ResponseEntity<Response> create(@RequestBody SpecialistRequest specialistRequest) throws ServiceException {
        Response response = specialistService.create(specialistRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
