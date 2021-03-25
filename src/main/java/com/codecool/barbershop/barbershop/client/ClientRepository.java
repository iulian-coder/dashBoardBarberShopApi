package com.codecool.barbershop.barbershop.client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    int countClientsByCreatedDateBetweenAndUser_Id(Date start, Date end,Long userId);

    boolean existsByPhoneNoAndUser_Id(String phoneNo, Long userId);

    List<Client> findAllByUser_Id(Long userId);

    Optional<Client> findByClientIdAndUser_Id(long clientId, Long userId);

//    Page<Client> findAllByUser_Id(Long userId, Pageable pageable);
}
