package com.codecool.barbershop.barbershop.security.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Data;



@Data
public class LoginRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Min(4)
    private String password;

}
