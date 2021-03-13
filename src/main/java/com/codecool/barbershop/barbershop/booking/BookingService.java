package com.codecool.barbershop.barbershop.booking;

import com.codecool.barbershop.barbershop.booking.payload.NewBookingRequest;
import com.codecool.barbershop.barbershop.booking.payload.ChangeBookingStatusRequest;
import com.codecool.barbershop.barbershop.client.Client;
import com.codecool.barbershop.barbershop.client.ClientService;
import com.codecool.barbershop.barbershop.client.payload.ClientProfileRequest;
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

    public List<Booking> getAllBookings(Pageable pageRequest, Long userId) {
        return bookingRepository.findAllByClient_User_Id(userId);
    }

    public Booking saveNewBooking(NewBookingRequest newBookingRequest, Long userId)  {

//        Preparing the data
        Date today = new Date();
        Booking newBooking = new Booking();
        Client client = clientService.getClientByIdAndUserId(newBookingRequest.getClientId(), userId);

//        Set the data & Save
        newBooking.setCreatedDate(today);
        newBooking.setUpdatedDate(today);
        newBooking.setBookingDate(newBookingRequest.getBookingDate());
        newBooking.setClient(client);
        newBooking.setBookingNotes(newBookingRequest.getBookingNotes());

        return bookingRepository.save(newBooking);
    }

    public Booking updateStatusBooking(ChangeBookingStatusRequest bookingRequest) {
        Optional<Booking> booking = bookingRepository.findById(bookingRequest.getId());
        if (booking.isEmpty()) return null;
        else {
            Date today = new Date();
            booking.get().setUpdatedDate(today);
            booking.get().setBookingStatus(BookingStatus.valueOf(bookingRequest.getStatus().toUpperCase(Locale.ROOT)));
            return bookingRepository.save(booking.get());
        }
    }

    public List<Booking> getAllBookingsByClientId(Long clientId, Sort sort ) {
        return bookingRepository.findAllByClient_ClientId(clientId, sort);
    }

    public int countBookingsByBookingDateBetweenAndBookingStatus(Date start, Date end, BookingStatus bookingStatus, Long userId) {
        return bookingRepository.countBookingsByBookingDateBetweenAndBookingStatusAndClient_User_Id(start, end, bookingStatus,userId);
    }

    public List<Booking> findAllByBookingDateBetweenAndBookingStatus(Date start, Date end, BookingStatus bookingStatus, Sort sort, Long userId) {
        return bookingRepository.findAllByBookingDateBetweenAndBookingStatusAndClient_User_Id(start, end, bookingStatus, sort, userId);

    }

    public ClientProfileRequest getClientDataAndBookings(long clientId, Long userId)  {
        Client client = clientService.getClientByIdAndUserId(clientId, userId);

        ClientProfileRequest clientProfileRequest = new ClientProfileRequest();

        clientProfileRequest.setClientId(client.getClientId());
        clientProfileRequest.setFirstName(client.getFirstName());
        clientProfileRequest.setLastName(client.getLastName());
        clientProfileRequest.setPhoneNo(client.getPhoneNo());
        clientProfileRequest.setEmail(client.getEmail());

        List<Booking> clientBookings = bookingRepository.findAllByClient_ClientId(clientId, null);
        clientProfileRequest.setBookingList(clientBookings);

        return clientProfileRequest;
    }


}
