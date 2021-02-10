package com.codecool.barbershop.barbershop.client;

import com.codecool.barbershop.barbershop.client.request.ClientSearchAutocompleteReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    public List<Client> getAllClients(Sort sort) {
        return clientRepository.findAll(sort);
    }

    public Client addClient(Client client) {
        Date today = new Date();
        client.setCreatedDate(today);
        client.setUpdatedDate(today);
        return clientRepository.save(client);
    }

    public Client updateClient(Client client) {
        return clientRepository.save(client);
    }

    public void deleteClient(Client client) {

        clientRepository.delete(client);
    }


    public Client getClientById(long clientId) throws Exception {
        Optional<Client> clientModel = clientRepository.findById(clientId);

        return clientModel.orElseThrow(() -> new Exception("Client not found id:" + clientId));
    }


    public List<ClientSearchAutocompleteReq> searchClientWithAutocomplete() {
        List<ClientSearchAutocompleteReq> clientSearchAutocompleteReqList = new ArrayList<>();
        List<Client> allClients = clientRepository.findAll();

        for (Client client : allClients) {
            ClientSearchAutocompleteReq autocompleteReq = new ClientSearchAutocompleteReq();
            autocompleteReq.setId(client.getClientId());
            autocompleteReq.setFirstName(client.getFirstName());
            autocompleteReq.setLastName(client.getLastName());
            autocompleteReq.setPhoneNo(client.getPhoneNo());
            autocompleteReq.setNameAndPhone(client.getFirstName() + " " + client.getLastName() + " | Phone: " + client.getPhoneNo());
            clientSearchAutocompleteReqList.add(autocompleteReq);
        }
        return clientSearchAutocompleteReqList;
    }


    public int countNewClientsDateBetween(Date start, Date end) {
        return clientRepository.countClientsByCreatedDateBetween(start, end);
    }
}
