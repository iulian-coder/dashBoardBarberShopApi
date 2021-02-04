package com.codecool.barbershop.barbershop.client;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@ToString
@Table(name="clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long clientId;

    @NotNull private String firstName;

    @NotNull private String lastName;

    @NotNull @Email
    @Column(unique = true) private String email;

    @NotNull private String phoneNo;




}
