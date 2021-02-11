package com.codecool.barbershop.barbershop.client;

import com.codecool.barbershop.barbershop.client.request.ClientSearchAutocompleteReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> getAllClients(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageRequest = PageRequest.of(page, size, sort);
        return clientService.getAllClients(pageRequest);
    }


    @GetMapping("{clientId}")
    public Client clientProfile(@PathVariable("clientId") long clientId) throws Exception {
        return clientService.getClientById(clientId);
    }

//    TODO make DTO
    @PostMapping
    public Client addClient(@RequestBody Client client) {
        return clientService.addClient(client);
    }

    @PutMapping
    public Client updateClient(@RequestBody Client client) {
        return clientService.updateClient(client);
    }

 @DeleteMapping
 public void deleteClient(@RequestBody Client client){
     clientService.deleteClient(client);
 }

    @GetMapping("search-client")
    public List<ClientSearchAutocompleteReq> searchClientWithAutocomplete() {
        return clientService.searchClientWithAutocomplete();
    }

}
