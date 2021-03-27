package com.codecool.barbershop.barbershop.client;

import com.codecool.barbershop.barbershop.client.payload.AddClientRequest;
import com.codecool.barbershop.barbershop.user.AuthProvider;
import com.codecool.barbershop.barbershop.user.User;
import com.codecool.barbershop.barbershop.user.UserPrincipal;
import com.codecool.barbershop.barbershop.user.UserService;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.emptyString;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {

    @MockBean
    private ClientService clientService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void getAllClients() {
    }

    @Test
    void clientProfile() {
    }

    @Test
    @DisplayName("Add new Client - POST /api/v1/clients")
    void addClient() throws Exception {
        User mockUser = new User(
                1L,
                "jon",
                "jon@gmail.com",
                "http//image.com",
                false,
                "password",
                AuthProvider.local,
                "1234-Uid-=provider");

        UserPrincipal mockUserPrincipal = UserPrincipal.create(mockUser);

        AddClientRequest mockClientRequest = new AddClientRequest("Ion",
                "Ion", "ion@ion.com", "40741852963");

        Client mockClient = new Client(1L, mockClientRequest.getFirstName(), mockClientRequest.getLastName(), mockClientRequest.getEmail()
                , mockClientRequest.getPhoneNo(), mockUser);

        doReturn(mockClient).when(clientService).addClient(mockClientRequest, mockUser.getId());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/clients")
                .with(user(mockUserPrincipal))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(mockClientRequest))
        )
                .andExpect(status().isCreated());
    }

    @Test
    void updateClient() {
    }

    @Test
    void deleteClient() {

    }

    @Test
    void searchClientWithAutocomplete() {
    }
}