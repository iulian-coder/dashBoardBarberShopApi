package com.codecool.barbershop.barbershop.security.payload;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class SignUpRequest {
    @NotNull(message = "Name is mandatory")
    @Size(message = "Name is mandatory",min = 3,max = 30)
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Min(message = "Password must be greater or equal to 4",value = 4)
    private String password;


}
