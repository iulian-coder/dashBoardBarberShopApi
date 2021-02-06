package com.codecool.barbershop.barbershop.dashboard;

import com.codecool.barbershop.barbershop.booking.BookingService;
import com.codecool.barbershop.barbershop.booking.BookingStatus;
import com.codecool.barbershop.barbershop.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

@Service
public class DashboardService {
    private final ClientService clientService;
    private final BookingService bookingService;

    @Autowired
    public DashboardService(ClientService clientService, BookingService bookingService) {
        this.clientService = clientService;
        this.bookingService = bookingService;
    }


    public DashboardData getDataForDashBoard() {
        DashboardData data = new DashboardData();

        Date firstDayOfTheMonth = java.sql.Date.valueOf(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()));
        Date lastDayOfTheMonth = java.sql.Date.valueOf(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));

        data.setReportDate(java.sql.Date.valueOf(LocalDate.now()));
        data.setTotalClients(clientService.getTotalClients());
        data.setTotalConfirmedBookings(bookingService.countBookingsByBookingDateBetweenAndBookingStatus(firstDayOfTheMonth, lastDayOfTheMonth, BookingStatus.CONFIRM));
        data.setTotalUpcomingBookings(bookingService.countBookingsByBookingDateBetweenAndBookingStatus(firstDayOfTheMonth, lastDayOfTheMonth, BookingStatus.UPCOMING));
        data.setTotalCanceledBookings(bookingService.countBookingsByBookingDateBetweenAndBookingStatus(firstDayOfTheMonth, lastDayOfTheMonth, BookingStatus.CANCEL));
        data.setLatestBookings(bookingService.findTop9ByBookingStatusOrderByBookingDateAsc(BookingStatus.UPCOMING));

        return data;
    }

}
