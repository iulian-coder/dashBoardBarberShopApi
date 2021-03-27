package com.codecool.barbershop.barbershop.booking;

import com.codecool.barbershop.barbershop.audit.AuditModel;
import com.codecool.barbershop.barbershop.client.Client;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "bookings")
@AllArgsConstructor
@NoArgsConstructor
public class Booking extends AuditModel {

    @Id
    @GeneratedValue
    @Setter(value = AccessLevel.NONE)
    private int id;

    private String bookingNotes;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date bookingDate;


    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus = BookingStatus.UPCOMING;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
    private Client client;
}
