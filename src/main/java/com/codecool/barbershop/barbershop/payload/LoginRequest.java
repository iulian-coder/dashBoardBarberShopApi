package com.codecool.barbershop.barbershop.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;


public class LoginRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Min(4)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

}
