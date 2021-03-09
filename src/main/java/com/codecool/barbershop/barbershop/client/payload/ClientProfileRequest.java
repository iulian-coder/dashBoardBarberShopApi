package com.codecool.barbershop.barbershop.client.payload;

import com.codecool.barbershop.barbershop.booking.Booking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientProfileRequest {

    private long clientId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private List<Booking> bookingList;

}
