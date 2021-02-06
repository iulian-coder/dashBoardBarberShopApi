package com.codecool.barbershop.barbershop.booking;

import com.codecool.barbershop.barbershop.booking.request.BookingReqAddNewBooking;
import com.codecool.barbershop.barbershop.booking.request.BookingReqChangeStatus;
import com.codecool.barbershop.barbershop.dashboard.DashboardData;
import com.codecool.barbershop.barbershop.client.Client;
import com.codecool.barbershop.barbershop.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ClientService clientService;

    @Autowired
    public BookingService(BookingRepository bookingRepository, ClientService clientService) {
        this.bookingRepository = bookingRepository;
        this.clientService = clientService;
    }

    public List<Booking> getAllBookings(Pageable pageRequest) {
        return bookingRepository.findAll(pageRequest).getContent();
    }

    public Booking saveNewBooking(BookingReqAddNewBooking bookingReqAddNewBooking) throws Exception {

//        Preparing the data
        Date today = new Date();
        Booking newBooking = new Booking();
        Client client = clientService.getClientById(bookingReqAddNewBooking.getClientId());

//        Set the data & Save
        newBooking.setCreatedDate(today);
        newBooking.setUpdatedDate(today);
        newBooking.setBookingDate(bookingReqAddNewBooking.getBookingDate());
        newBooking.setClient(client);
        newBooking.setBookingNotes(bookingReqAddNewBooking.getBookingNotes());

        return bookingRepository.save(newBooking);
    }

    public Booking updateStatusBooking(BookingReqChangeStatus bookingRequest) {
        Optional<Booking> booking = bookingRepository.findById(bookingRequest.getId());
        if (booking.isEmpty()) return null;
        else {
            Date today = new Date();
            booking.get().setUpdatedDate(today);
            booking.get().setBookingStatus(BookingStatus.valueOf(bookingRequest.getStatus().toUpperCase(Locale.ROOT)));
            return bookingRepository.save(booking.get());
        }
    }

    public List<Booking> getAllBookingsByClientId(Long clientId, Sort sort) {
        return bookingRepository.findAllByClient_ClientId(clientId,sort);
    }


    public int countBookingsByBookingDateBetweenAndBookingStatus(Date firstDayOfTheMonth, Date lastDayOfTheMonth, BookingStatus confirm) {
        return bookingRepository.countBookingsByBookingDateBetweenAndBookingStatus(firstDayOfTheMonth, lastDayOfTheMonth, confirm);
    }

    public List<Booking> findTop9ByBookingStatusOrderByBookingDateAsc(BookingStatus upcoming) {
        return bookingRepository.findTop9ByBookingStatusOrderByBookingDateAsc(upcoming);
    }
}
