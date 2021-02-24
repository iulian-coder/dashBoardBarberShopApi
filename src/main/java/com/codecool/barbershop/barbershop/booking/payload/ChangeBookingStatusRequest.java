package com.codecool.barbershop.barbershop.booking.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeBookingStatusRequest {

    @NotBlank
    private int id;
    @NotBlank
    private String status;
}
