package com.blubank.doctorappointment;


import com.blubank.doctorappointment.model.AppointmentRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestEndPoint extends DoctorAppointmentApplicationTests{

    private static final Logger logger = LogManager.getLogger(TestEndPoint.class);

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private final String mobile = "09122786518";
    private final Long doctorId = 101L;
    private final String date = "2023-04-14";
    private final String start = "10:00";
    private final String end = "13:00";

    @Test
    @Order(1)
    public void addOpenTime_Success() throws Exception {
        logger.info("start addOpenTime ");
        addOpenTime(doctorId, date, start, end, HttpStatus.SC_OK, 200);
        logger.info("finish addOpenTime ");
    }


    void addOpenTime(Long doctorId, String date, String start, String end, int expectedHttpStatus, int expectedResult) throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        AppointmentRequest appointmentRequest = new AppointmentRequest(doctorId, date, start, end);
        logger.info("start add open time to a doctor with data ({})", appointmentRequest.toString());
        mockMvc.perform(post("/api/appointment/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(appointmentRequest))).andDo(print())
                .andExpect(status().is(expectedHttpStatus))
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.result").value(expectedResult));
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

}
