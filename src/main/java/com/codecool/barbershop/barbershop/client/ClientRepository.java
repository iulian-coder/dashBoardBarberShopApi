package com.codecool.barbershop.barbershop.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    int countClientsByCreatedDateBetween(Date start, Date end);


    boolean existsByPhoneNo(String phoneNumber);
}
