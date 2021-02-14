package com.codecool.barbershop.barbershop.booking;

import com.codecool.barbershop.barbershop.booking.request.BookingReqAddNewBooking;
import com.codecool.barbershop.barbershop.booking.request.BookingReqChangeStatus;
import com.codecool.barbershop.barbershop.client.Client;
import com.codecool.barbershop.barbershop.client.ClientService;
import com.codecool.barbershop.barbershop.client.request.ClientProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

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
        return bookingRepository.findAllByClient_ClientId(clientId, sort);
    }

    public int countBookingsByBookingDateBetweenAndBookingStatus(Date start, Date end, BookingStatus bookingStatus) {
        return bookingRepository.countBookingsByBookingDateBetweenAndBookingStatus(start, end, bookingStatus);
    }

    public List<Booking> findAllByBookingDateBetweenAndBookingStatus(Date start, Date end, BookingStatus bookingStatus, Sort sort) {
        return bookingRepository.findAllByBookingDateBetweenAndBookingStatus(start, end, bookingStatus, sort);

    }

    public ClientProfile getClientDataAndBookings(long clientId) throws Exception {
        ClientProfile clientProfile = new ClientProfile();
        Client client = clientService.getClientById(clientId);

        clientProfile.setClientId(client.getClientId());
        clientProfile.setFirstName(client.getFirstName());
        clientProfile.setLastName(client.getLastName());
        clientProfile.setPhoneNo(client.getPhoneNo());
        clientProfile.setEmail(client.getEmail());

        List<Booking> clientBookings = bookingRepository.findAllByClient_ClientId(clientId, null);
        clientProfile.setBookingList(clientBookings);

        return clientProfile;
    }


}
