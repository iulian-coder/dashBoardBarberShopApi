package com.codecool.barbershop.barbershop.dashboard;

import com.codecool.barbershop.barbershop.booking.BookingService;
import com.codecool.barbershop.barbershop.booking.BookingStatus;
import com.codecool.barbershop.barbershop.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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


    public DashboardData getDataForDashBoard(Date reportDate, Date start, Date end, Sort sort) {
        DashboardData data = new DashboardData();

        data.setReportDate(reportDate);
        data.setNewClients(clientService.countNewClientsDateBetween(start,end));
        data.setTotalConfirmedBookings(bookingService.countBookingsByBookingDateBetweenAndBookingStatus(start, end, BookingStatus.CONFIRM));
        data.setTotalUpcomingBookings(bookingService.countBookingsByBookingDateBetweenAndBookingStatus(start, end, BookingStatus.UPCOMING));
        data.setTotalCanceledBookings(bookingService.countBookingsByBookingDateBetweenAndBookingStatus(start, end, BookingStatus.CANCEL));
        data.setBookingList(bookingService.findAllByBookingDateBetweenAndBookingStatus(start, end, BookingStatus.UPCOMING, sort));

        return data;
    }

}
