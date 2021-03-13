package com.codecool.barbershop.barbershop.client.payload;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@Data
public class ClientModifyRequest {

    @NotNull
    private long clientId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
}
