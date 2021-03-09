package com.codecool.barbershop.barbershop.client;

import com.codecool.barbershop.barbershop.client.payload.AddClientRequest;
import com.codecool.barbershop.barbershop.client.payload.ClientSearchAutocompleteRequest;
import com.codecool.barbershop.barbershop.exception.RecordNotFoundException;
import com.codecool.barbershop.barbershop.security.JwtTokenService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;


    public List<Client> getAllClients(Pageable pageRequest) {
        return clientRepository.findAll(pageRequest).getContent();
    }

    public Client addClient(AddClientRequest newClient) {
        Date today = new Date();
        Client client = new Client();
        client.setCreatedDate(today);
        client.setUpdatedDate(today);
        client.setFirstName(newClient.getFirstName());
        client.setLastName(newClient.getLastName());
        client.setEmail(newClient.getEmail());
        client.setPhoneNo(newClient.getPhoneNo());


        return clientRepository.save(client);
    }

    public Client updateClient(Client client) {
        return clientRepository.save(client);
    }

    public void deleteClient(Client client) {

        clientRepository.delete(client);
    }

    public boolean existsByPhoneNo(String phoneNumber){
        return clientRepository.existsByPhoneNo(phoneNumber);
    }


    public Client getClientById(long clientId) {
        Optional<Client> clientModel = clientRepository.findById(clientId);

        return clientModel.orElseThrow(() -> new RecordNotFoundException("Client not found id:" + clientId));
    }


    public List<ClientSearchAutocompleteRequest> searchClientWithAutocomplete() {
        List<ClientSearchAutocompleteRequest> clientSearchAutocompleteRequestList = new ArrayList<>();
        List<Client> allClients = clientRepository.findAll();

        for (Client client : allClients) {
            ClientSearchAutocompleteRequest autocompleteReq = new ClientSearchAutocompleteRequest();
            autocompleteReq.setId(client.getClientId());
            autocompleteReq.setFirstName(client.getFirstName());
            autocompleteReq.setLastName(client.getLastName());
            autocompleteReq.setPhoneNo(client.getPhoneNo());
            autocompleteReq.setNameAndPhone(client.getFirstName() + " " + client.getLastName() + " | Phone: " + client.getPhoneNo());
            clientSearchAutocompleteRequestList.add(autocompleteReq);
        }
        return clientSearchAutocompleteRequestList;
    }


    public int countNewClientsDateBetween(Date start, Date end) {
        return clientRepository.countClientsByCreatedDateBetween(start, end);
    }


}
