package com.codecool.barbershop.barbershop.dashboard;

import com.codecool.barbershop.barbershop.booking.BookingService;
import com.codecool.barbershop.barbershop.booking.BookingStatus;
import com.codecool.barbershop.barbershop.client.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class DashboardService {
    private final ClientService clientService;
    private final BookingService bookingService;


    public DashboardData getDataForDashBoard(Date reportDate, Date start, Date end, Sort sort, Long userId) {
        DashboardData data = new DashboardData();

        data.setReportDate(reportDate);
        data.setNewClients(clientService.countNewClientsDateBetween(start, end, userId));
        data.setTotalConfirmedBookings(bookingService.countBookingsByBookingDateBetweenAndBookingStatus(start, end, BookingStatus.CONFIRM));
        data.setTotalUpcomingBookings(bookingService.countBookingsByBookingDateBetweenAndBookingStatus(start, end, BookingStatus.UPCOMING));
        data.setTotalCanceledBookings(bookingService.countBookingsByBookingDateBetweenAndBookingStatus(start, end, BookingStatus.CANCEL));
        data.setBookingList(bookingService.findAllByBookingDateBetweenAndBookingStatus(start, end, BookingStatus.UPCOMING, sort));

        return data;
    }

}
