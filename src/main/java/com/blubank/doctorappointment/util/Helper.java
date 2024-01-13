package com.blubank.doctorappointment.util;

import com.blubank.doctorappointment.model.Response;
import org.springframework.stereotype.Component;

@Component
public class Helper {

    public Response fillResponse(int result, String description) {
        Response response = new Response();
        response.setResult(String.valueOf(result));
        response.setDescription(description);

        return response;
    }
}
