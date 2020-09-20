package com.pyruz.rest.secured.exception;

import com.google.gson.Gson;
import com.pyruz.rest.secured.configuration.ApplicationContextHolder;
import com.pyruz.rest.secured.model.dto.BaseDTO;
import com.pyruz.rest.secured.model.dto.MetaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint extends ApplicationContextHolder implements AuthenticationEntryPoint {

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