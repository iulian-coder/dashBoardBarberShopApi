package com.codecool.barbershop.barbershop.client;

import com.codecool.barbershop.barbershop.client.payload.AddClientRequest;
import com.codecool.barbershop.barbershop.client.payload.ClientSearchAutocompleteRequest;
import com.codecool.barbershop.barbershop.exception.RecordNotFoundException;
import com.codecool.barbershop.barbershop.user.User;
import com.codecool.barbershop.barbershop.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final UserService userService;


    public List<Client> getAllClientsByUserId(Long userId) {
        return clientRepository.findAllByUser_Id(userId);
    }

    public Client addClient(AddClientRequest newClient, Long userId) {
        Date today = new Date();
        Client client = new Client();
        User user = userService.getUserById(userId);
        client.setCreatedDate(today);
        client.setUpdatedDate(today);
        client.setFirstName(newClient.getFirstName());
        client.setLastName(newClient.getLastName());
        client.setEmail(newClient.getEmail());
        client.setPhoneNo(newClient.getPhoneNo());
        client.setUser(user);


        return clientRepository.save(client);
    }

    public Client updateClient(Client client) {
        return clientRepository.save(client);
    }

    public void deleteClient(Client client) {

        clientRepository.delete(client);
    }


    public Client getClientByIdAndUserId(long clientId, Long userId) {
        Optional<Client> clientModel = clientRepository.findByClientIdAndUser_Id(clientId, userId);

        return clientModel.orElseThrow(() -> new RecordNotFoundException("Client not found id:" + clientId));
    }


    public List<ClientSearchAutocompleteRequest> searchClientWithAutocomplete(Long userId) {
        List<ClientSearchAutocompleteRequest> clientSearchAutocompleteRequestList = new ArrayList<>();
        List<Client> allClients = getAllClientsByUserId(userId);

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


    public int countNewClientsDateBetweenAndUserId(Date start, Date end, Long userId) {
        return clientRepository.countClientsByCreatedDateBetweenAndUser_Id(start, end, userId);
    }


    public boolean existsByPhoneNoAndUserId(String phoneNo, Long userId) {
        boolean b = clientRepository.existsByPhoneNoAndUser_Id(phoneNo, userId);
        return b;
    }
}
