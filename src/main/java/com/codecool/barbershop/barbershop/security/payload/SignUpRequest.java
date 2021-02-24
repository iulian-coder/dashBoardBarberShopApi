package com.codecool.barbershop.barbershop.security.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class SignUpRequest {
    @NotBlank(message = "Name is mandatory")
    @Min(message = "Name must be greater or equal to 3",value = 3)
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Min(message = "Password must be greater or equal to 4",value = 4)
    private String password;


}
