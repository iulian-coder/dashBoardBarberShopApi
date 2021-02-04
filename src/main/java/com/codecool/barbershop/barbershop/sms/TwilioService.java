package com.codecool.barbershop.barbershop.sms;

public interface TwilioService {

    void sendSms(String to, String from, String body);
}
