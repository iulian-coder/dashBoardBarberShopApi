package com.codecool.barbershop.barbershop.search;

import com.codecool.barbershop.barbershop.client.ClientService;
import com.codecool.barbershop.barbershop.client.payload.ClientSearchResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SearchService {

    private final ClientService clientService;

    public List<ClientSearchResponse> searchClientWithAutoComplete(Long userPrincipalId) {
        return clientService.searchClientWithAutocomplete(userPrincipalId);
    }
}
