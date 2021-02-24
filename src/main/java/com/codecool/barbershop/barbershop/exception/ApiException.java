package com.codecool.barbershop.barbershop.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class ApiException {

    private String message;
    private HttpStatus httpStatus;
    private ZonedDateTime timestamp;

}
