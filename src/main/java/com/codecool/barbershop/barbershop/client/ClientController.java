package com.codecool.barbershop.barbershop.client;

import com.codecool.barbershop.barbershop.booking.BookingService;
import com.codecool.barbershop.barbershop.client.payload.AddClientRequest;
import com.codecool.barbershop.barbershop.client.payload.ClientProfileRequest;
import com.codecool.barbershop.barbershop.client.payload.ClientSearchAutocompleteRequest;
import com.codecool.barbershop.barbershop.exception.BadRequestException;
import com.codecool.barbershop.barbershop.security.CurrentUser;
import com.codecool.barbershop.barbershop.user.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
                                      @RequestParam(defaultValue = "10") int size) {
//        TODO Sort option input from User
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageRequest = PageRequest.of(page, size, sort);
        return clientService.getAllClients(pageRequest);
    }


    @GetMapping("{clientId}")
    public ClientProfileRequest clientProfile(@PathVariable("clientId") long clientId) {
        return bookingService.getClientDataAndBookings(clientId);
    }


    @PostMapping
    public Client addClient(@Valid @RequestBody AddClientRequest newClient, @CurrentUser UserPrincipal userPrincipal) {
        if (clientService.existsByPhoneNoAndUserId(newClient.getPhoneNo(), userPrincipal.getId())) {

            throw new BadRequestException("Phone number already in use.");
        }
//
        return clientService.addClient(newClient, userPrincipal.getId());
    }

    @PutMapping
    public Client updateClient(@RequestBody Client client) {
        return clientService.updateClient(client);
    }

    @DeleteMapping
    public void deleteClient(@RequestBody Client client) {
        clientService.deleteClient(client);
    }

    @GetMapping("search-client")
    public List<ClientSearchAutocompleteRequest> searchClientWithAutocomplete() {
        return clientService.searchClientWithAutocomplete();
    }

}
