package com.codecool.barbershop.barbershop.search;

import com.codecool.barbershop.barbershop.client.payload.ClientSearchResponse;
import com.codecool.barbershop.barbershop.user.CurrentUser;
import com.codecool.barbershop.barbershop.user.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/search")
@AllArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/client")
    public List<ClientSearchResponse> searchClientWithAutoComplete(@CurrentUser UserPrincipal userPrincipal) {
        Long userPrincipalId = userPrincipal.getId();
        return searchService.searchClientWithAutoComplete(userPrincipalId);
    }
}
