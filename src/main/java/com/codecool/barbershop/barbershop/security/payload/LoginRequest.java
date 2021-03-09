package com.codecool.barbershop.barbershop.security.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Data;



@Data
public class LoginRequest {
    @NotBlank(message = "Email is mandatory")
    @Email
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Min(message = "Password wrong",value = 4)
    private String password;

}
