package com.pyruz.rest.secured.exception;

import com.google.gson.Gson;
import com.pyruz.rest.secured.configuration.ApplicationProperties;
import com.pyruz.rest.secured.model.dto.BaseDTO;
import com.pyruz.rest.secured.model.dto.MetaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    final ApplicationProperties applicationProperties;

    public CustomAuthenticationEntryPoint(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException {
        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.getWriter().write(
                new Gson().toJson(new BaseDTO(new MetaDTO(
                                applicationProperties.getProperty("application.message.accessDenied.text")
                        ), "")
                )
        );
    }
}
