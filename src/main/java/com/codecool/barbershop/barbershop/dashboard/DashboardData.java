package com.codecool.barbershop.barbershop.dashboard;

import com.codecool.barbershop.barbershop.booking.Booking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardData {

    private int newClients;
    private int totalUpcomingBookings;
    private int totalConfirmedBookings;
    private int totalCanceledBookings;
    private Date reportDate;
    private List<Booking> latestBookings;

}

