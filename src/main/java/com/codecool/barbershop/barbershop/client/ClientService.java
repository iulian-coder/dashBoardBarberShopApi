package com.codecool.barbershop.barbershop.client;

import com.codecool.barbershop.barbershop.client.payload.AddClientRequest;
import com.codecool.barbershop.barbershop.client.payload.ClientModifyRequest;
import com.codecool.barbershop.barbershop.client.payload.ClientSearchResponse;
import com.codecool.barbershop.barbershop.exception.RecordNotFoundException;
import com.codecool.barbershop.barbershop.user.User;
import com.codecool.barbershop.barbershop.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


    public List<Client> getAllClientsByUserId(Long userId ) {
        return clientRepository.findAllByUser_Id(userId);
    }

    public Client getClientByIdAndUserId(long clientId, Long userId) {
        Optional<Client> clientModel = clientRepository.findByClientIdAndUser_Id(clientId, userId);

        return clientModel.orElseThrow(() -> new RecordNotFoundException("Client not found id:" + clientId));
    }

    public Client addClient(AddClientRequest newClient, Long userId) {
        Client client = new Client();
        User user = userService.findUserById(userId);

        client.setFirstName(newClient.getFirstName());
        client.setLastName(newClient.getLastName());
        client.setEmail(newClient.getEmail());
        client.setPhoneNo(newClient.getPhoneNo());
        client.setUser(user);

        return clientRepository.save(client);
    }


    public Client updateClient(ClientModifyRequest client, Long userId) {
        Client clientModel = getClientByIdAndUserId(client.getClientId(), userId);

        clientModel.setEmail(client.getEmail());
        clientModel.setPhoneNo(clientModel.getPhoneNo());
        clientModel.setFirstName(client.getFirstName());
        clientModel.setLastName(clientModel.getLastName());

        return clientRepository.save(clientModel);
    }


    public List<ClientSearchResponse> searchClientWithAutocomplete(Long userId) {
        List<ClientSearchResponse> clientSearchResponseList = new ArrayList<>();
        List<Client> allClients = getAllClientsByUserId(userId);

        for (Client client : allClients) {
            ClientSearchResponse autocompleteReq = new ClientSearchResponse();
            autocompleteReq.setId(client.getClientId());
            autocompleteReq.setFirstName(client.getFirstName());
            autocompleteReq.setLastName(client.getLastName());
            autocompleteReq.setPhoneNo(client.getPhoneNo());
            autocompleteReq.setNameAndPhone(client.getFirstName() + " " + client.getLastName() + " | Phone: " + client.getPhoneNo());
            clientSearchResponseList.add(autocompleteReq);
        }
        return clientSearchResponseList;
    }


    public int countNewClientsDateBetweenAndUserId(Date start, Date end, Long userId) {
        return clientRepository.countClientsByCreatedDateBetweenAndUser_Id(start, end, userId);
    }


    public boolean existsByPhoneNoAndUserId(String phoneNo, Long userId) {
        return clientRepository.existsByPhoneNoAndUser_Id(phoneNo, userId);
    }

    public void deleteClient(ClientModifyRequest client, Long id) {
        Client clientToDelete = getClientByIdAndUserId(client.getClientId(), id);
        clientRepository.delete(clientToDelete);
    }

//    public Page<Client> getAllClientsByUserIdSorted(Long userId, Pageable pageable) {
//
//        return clientRepository.findAllByUser_Id(userId,pageable);
//    }
}
