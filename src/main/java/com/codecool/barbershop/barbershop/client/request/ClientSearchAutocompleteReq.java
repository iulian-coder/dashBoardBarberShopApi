package com.codecool.barbershop.barbershop.client.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientSearchAutocompleteReq {

    private Long id;
    private String firstName;
    private String phoneNo;
    private String firstNameAndPhone;


}
