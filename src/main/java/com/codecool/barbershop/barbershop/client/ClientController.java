package com.codecool.barbershop.barbershop.client;

import com.codecool.barbershop.barbershop.booking.BookingService;
import com.codecool.barbershop.barbershop.client.payload.*;
import com.codecool.barbershop.barbershop.exception.BadRequestException;
import com.codecool.barbershop.barbershop.user.CurrentUser;
import com.codecool.barbershop.barbershop.user.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/clients")
@AllArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final BookingService bookingService;


    @GetMapping
    public List<Client> getAllClients(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size, @CurrentUser UserPrincipal userPrincipal) {

        Long userId = userPrincipal.getId();
        //        TODO Sort option input from User
//        Sort sort= Sort.by("createdDate");
//        Pageable pageable = PageRequest.of(page, size, sort);
//       Page<Client> clientService.getAllClientsByUserIdSorted(userId, pageable);

        return clientService.getAllClientsByUserId(userId);
    }


    @GetMapping("/{clientId}")
    public ClientProfileRequest clientProfile(@PathVariable("clientId") long clientId, @CurrentUser UserPrincipal userPrincipal) {
        return bookingService.getClientDataAndBookings(clientId, userPrincipal.getId());
    }


    @PostMapping
    public ResponseEntity<?> addClient(@Valid @RequestBody AddClientRequest newClient, @CurrentUser UserPrincipal userPrincipal) {
        if (clientService.existsByPhoneNoAndUserId(newClient.getPhoneNo(), userPrincipal.getId())) {
            throw new BadRequestException("Phone number already in use.");
        }
        Client client = clientService.addClient(newClient, userPrincipal.getId());
        return new ResponseEntity<>(client, HttpStatus.CREATED);

    }

    @PutMapping
    public ResponseEntity<ClientModifyResponse> updateClient(@RequestBody ClientModifyRequest client, @CurrentUser UserPrincipal userPrincipal) {
        Client updateClient = clientService.updateClient(client, userPrincipal.getId());
        return ResponseEntity.ok(new ClientModifyResponse(true, "Client modify success  " + updateClient.getFirstName()));
    }

    @DeleteMapping
    public void deleteClient(@RequestBody ClientModifyRequest client, @CurrentUser UserPrincipal userPrincipal) {
        clientService.deleteClient(client, userPrincipal.getId());
    }



}
