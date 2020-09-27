package com.pyruz.rest.secured.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Builder
public class ServiceException extends RuntimeException{
    private String message;
    private HttpStatus httpStatus;
}
