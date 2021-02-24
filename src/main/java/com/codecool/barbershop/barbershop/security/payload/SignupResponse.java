package com.codecool.barbershop.barbershop.security.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SignupResponse {
    private boolean success;
    private String message;

}
