package com.codecool.barbershop.barbershop.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    int countClientsByCreatedDateBetweenAndUser_Id(Date start, Date end,Long userId);

    boolean existsByPhoneNoAndUser_Id(String phoneNo, Long userId);
}
