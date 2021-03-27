package com.codecool.barbershop.barbershop.client.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientSearchResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String displayedResult;


}
