package com.codecool.barbershop.barbershop.client;

import com.codecool.barbershop.barbershop.client.request.ClientSearchAutocompleteReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    public List<Client> getAllClients() {
        return clientRepository.findAll(Sort.by("firstName"));
    }


    public Client getClientById(long clientId) throws Exception {
        Optional<Client> clientModel = clientRepository.findById(clientId);

        return clientModel.orElseThrow(() -> new Exception("Client not found id:" + clientId));
    }


    public List<ClientSearchAutocompleteReq> searchClientWithAutocomplete() {
        List<ClientSearchAutocompleteReq> clientList = new ArrayList<>();
        List<Client> allClients = getAllClients();

        for (Client client : allClients) {
            ClientSearchAutocompleteReq clientSearchAutocompleteReq = new ClientSearchAutocompleteReq();
            clientSearchAutocompleteReq.setId(client.getClientId());
            clientSearchAutocompleteReq.setFirstName(client.getFirstName());
            clientSearchAutocompleteReq.setPhoneNo(client.getPhoneNo());
            clientSearchAutocompleteReq.setFirstNameAndPhone("Name: "+client.getFirstName()+" | Phone: "+client.getPhoneNo());

            clientList.add(clientSearchAutocompleteReq);
        }
        return clientList;
    }
    public int getTotalClients() {
        return clientRepository.findAll().size();
    }

    public Client updateClient(Client client) {
        return clientRepository.save(client);
    }

    public void deleteClient(Client client) {

        clientRepository.delete(client);
    }

    public Client addClient(Client client) {
        System.out.println(client);
        return clientRepository.save(client);
    }
}
