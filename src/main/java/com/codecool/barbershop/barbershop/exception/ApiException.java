package com.codecool.barbershop.barbershop.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class ApiException {

    private String message;
//    private Throwable throwable;
    private HttpStatus httpStatus;
    private ZonedDateTime timestamp;


}
