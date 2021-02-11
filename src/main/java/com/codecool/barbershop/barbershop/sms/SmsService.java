package com.codecool.barbershop.barbershop.sms;

import com.codecool.barbershop.barbershop.booking.Booking;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService implements TwilioService {


    private final String ACCOUNT_SID = System.getenv("ACCOUNT_SID");

    private final String AUTH_TOKEN = System.getenv("AUTH_TOKEN");


    @Override
    public void sendSms(String to, String from, String body) {

        try {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message message = Message
                    .creator(new PhoneNumber(to),
                            new PhoneNumber(from),
                            body)
                    .create();

            System.out.println(message.getSid());

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public void sendSmsNewBooking(Booking newBooking) {
        String from = "+18059549771";
        String clientName = newBooking.getClient().getFirstName();
        String phoneNo = "+"+ newBooking.getClient().getPhoneNo();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String bookingDate = dateFormat.format(newBooking.getBookingDate());

        String message = "Buna " + clientName + " ,rezervarea ta este inregistrata, te asteptam la data " + bookingDate;
        sendSms(phoneNo, from, message);
    }
}
