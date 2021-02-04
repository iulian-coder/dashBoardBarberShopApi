package com.codecool.barbershop.barbershop.client;

import com.codecool.barbershop.barbershop.client.request.ClientSearchAutocompleteReq;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Client> getAllClients() {
        return clientService.getAllClients();
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
