package com.pyruz.rest.secured.utility;

import com.pyruz.rest.secured.model.dto.BaseDTO;
import com.pyruz.rest.secured.security.JwtTokenProvider;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Utilities {


    public static Utilities getInstance() {
        return new Utilities();
    }

    public String logObject(BaseDTO baseDTO, HttpServletRequest request, JwtTokenProvider jwtTokenProvider) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String username = jwtTokenProvider.resolveToken(request) != null ? jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request)) : "anonymous";
        return "\n# -> Request datetime : " + dateFormat.format(new Date()) + "\n" +
                "url ----------------------------------------------------------" + request.getRequestURL() + "\n" +
                "message ------------------------------------------------------" + baseDTO.getMeta().getMessage() + "\n" +
                "username -----------------------------------------------------" + username + "\n";
    }
}
