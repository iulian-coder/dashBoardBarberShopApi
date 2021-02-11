package com.codecool.barbershop.barbershop.booking;

import com.codecool.barbershop.barbershop.booking.request.BookingReqAddNewBooking;
import com.codecool.barbershop.barbershop.booking.request.BookingReqChangeStatus;
import com.codecool.barbershop.barbershop.dashboard.DashboardData;
import com.codecool.barbershop.barbershop.sms.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/bookings")
@CrossOrigin("*")
public class BookingController {

    private final BookingService bookingService;
    private final SmsService smsService;

    @Autowired
    public BookingController(BookingService bookingService, SmsService smsService) {
        this.bookingService = bookingService;
        this.smsService = smsService;
    }

    @GetMapping
    List<Booking> getAllBooking(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageRequest = PageRequest.of(page, size, sort);
        return bookingService.getAllBookings(pageRequest);
    }

    @GetMapping("history/{clientId}")
    List<Booking> getAllBookingsByClientId(@PathVariable Long clientId) {
        Sort sort = Sort.by("createdDate").descending();
        return bookingService.getAllBookingsByClientId(clientId, sort);
    }

    @PostMapping
    public Booking saveNewBooking(@RequestBody BookingReqAddNewBooking bookingReqAddNewBooking) throws Exception {
//        Add booking
        Booking newBooking = bookingService.saveNewBooking(bookingReqAddNewBooking);
//        Send Sms notification
//        smsService.sendSmsNewBooking(newBooking);
        return newBooking;

    }

    @PutMapping
    public Booking updateStatusBooking(@RequestBody BookingReqChangeStatus booking) {
        return bookingService.updateStatusBooking(booking);

    }

}