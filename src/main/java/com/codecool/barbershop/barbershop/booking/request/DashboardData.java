package com.codecool.barbershop.barbershop.booking.request;

import com.codecool.barbershop.barbershop.booking.Booking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardData {

    private int totalClients;
    private int totalUpcomingBookings;
    private int totalConfirmedBookings;
    private int totalCanceledBookings;
    private List<Booking> latestBookings;

}

