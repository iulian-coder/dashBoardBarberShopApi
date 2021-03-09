package com.codecool.barbershop.barbershop.booking.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewBookingRequest {


    private int clientId;
    private Date bookingDate;
    private String bookingNotes;
    private boolean sendSms;

}
