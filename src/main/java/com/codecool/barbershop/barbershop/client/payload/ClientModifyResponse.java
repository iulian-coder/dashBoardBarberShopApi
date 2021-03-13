package com.codecool.barbershop.barbershop.client.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ClientModifyResponse {

    private boolean success;
    private String message;

}
