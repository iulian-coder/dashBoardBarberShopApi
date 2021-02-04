package com.codecool.barbershop.barbershop.client.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ClientRequest {
    private long clientId;
    private String firstName, lastName, email, phoneNo;
}
