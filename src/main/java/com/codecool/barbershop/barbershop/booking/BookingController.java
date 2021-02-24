package com.codecool.barbershop.barbershop.booking;

import com.codecool.barbershop.barbershop.booking.payload.NewBookingRequest;
import com.codecool.barbershop.barbershop.booking.payload.ChangeBookingStatusRequest;
import com.codecool.barbershop.barbershop.sms.SmsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/bookings")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final SmsService smsService;

    @GetMapping
    List<Booking> getAllBooking(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
//        TODO Sort option input from User
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageRequest = PageRequest.of(page, size, sort);
        return bookingService.getAllBookings(pageRequest);
    }

    @GetMapping("history/{clientId}")
    List<Booking> getAllBookingsByClientId(@PathVariable Long clientId) {
//        TODO Sort option input from User
        Sort sort = Sort.by("createdDate").descending();
        return bookingService.getAllBookingsByClientId(clientId, sort);
    }

    @PostMapping
    public Booking saveNewBooking(@RequestBody NewBookingRequest newBookingRequest) throws Exception {
        Booking newBooking = bookingService.saveNewBooking(newBookingRequest);
//        Send Sms notification
        if (newBookingRequest.isSendSms()) smsService.sendSmsNewBooking(newBooking);
        return newBooking;

    }

    @PutMapping
    public Booking updateStatusBooking(@RequestBody ChangeBookingStatusRequest booking) {
        return bookingService.updateStatusBooking(booking);

    }

}