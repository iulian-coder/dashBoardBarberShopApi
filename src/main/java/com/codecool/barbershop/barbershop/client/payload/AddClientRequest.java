package com.codecool.barbershop.barbershop.client.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddClientRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
}
