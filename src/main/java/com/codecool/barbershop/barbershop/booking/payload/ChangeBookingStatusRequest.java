package com.codecool.barbershop.barbershop.booking.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeBookingStatusRequest {

    @NotEmpty
    private int id;
    @NotEmpty
    private String status;
}
