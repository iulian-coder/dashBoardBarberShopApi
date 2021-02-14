package com.codecool.barbershop.barbershop.booking;


import com.sun.source.tree.LambdaExpressionTree;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

@Repository
interface BookingRepository extends JpaRepository<Booking, Integer> {


    List<Booking> findAllByClient_ClientId(Long i, Sort sort);

    int countBookingsByBookingDateBetweenAndBookingStatus(Date start, Date end, BookingStatus status);

    List<Booking> findAllByBookingDateBetweenAndBookingStatus(Date start, Date end, BookingStatus status, Sort sort);




}
