package com.codecool.barbershop.barbershop.client;

import com.codecool.barbershop.barbershop.client.payload.AddClientRequest;
import com.codecool.barbershop.barbershop.client.payload.ClientModifyRequest;
import com.codecool.barbershop.barbershop.client.payload.ClientSearchResponse;
import com.codecool.barbershop.barbershop.exception.RecordNotFoundException;
import com.codecool.barbershop.barbershop.user.User;
import com.codecool.barbershop.barbershop.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ClientService {

    private final ClientRepository clientRepository;
    private final UserService userService;


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


    public Client updateClient(ClientModifyRequest clientModifyRequest, Long userId) {
        Client clientModel = getClientByIdAndUserId(clientModifyRequest.getClientId(), userId);
        log.info("Update client " + clientModifyRequest + " userId " + userId);
        clientModel.setEmail(clientModifyRequest.getEmail());
        clientModel.setPhoneNo(clientModifyRequest.getPhoneNo());
        clientModel.setFirstName(clientModifyRequest.getFirstName());
        clientModel.setLastName(clientModifyRequest.getLastName());
        return clientRepository.save(clientModel);
    }


    public List<ClientSearchResponse> searchClientWithAutocomplete(Long userId) {
        List<Client> allClients = clientRepository.findAllByUser_Id(userId);
        return allClients.stream().map(item -> new ClientSearchResponse(item.getClientId(),
                item.getFirstName(), item.getLastName(), item.getPhoneNo(),
                item.getFirstName() + " " + item.getLastName() + " | Phone: +" + item.getPhoneNo()))
                .collect(Collectors.toList());
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

    public Page<Client> getAllClientsByUserIdPageable(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return clientRepository.findAllByUser_Id(userId, pageable);
    }
}
