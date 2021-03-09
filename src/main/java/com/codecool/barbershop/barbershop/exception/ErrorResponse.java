package com.codecool.barbershop.barbershop.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ErrorResponse {


    private String message;
    private List<String> details;


}
