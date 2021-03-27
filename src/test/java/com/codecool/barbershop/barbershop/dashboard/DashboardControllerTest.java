package com.codecool.barbershop.barbershop.dashboard;

import com.codecool.barbershop.barbershop.booking.Booking;
import com.codecool.barbershop.barbershop.client.Client;
import com.codecool.barbershop.barbershop.user.UserPrincipal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.*;
import java.util.Collections;

import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class DashboardControllerTest {

    @MockBean
    private DashboardService dashboardService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Get dashboard data - GET /api/v1/dashboard")
    void dashboardData() throws Exception {

//        Client client = new Client();
//        Booking booking = new Booking();

        UserPrincipal userPrincipal = new UserPrincipal(1L, "email@email.com"
                , "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        mockMvc.perform(get("/api/v1/dashboard").with(user(userPrincipal)))
                .andExpect(status().isOk())
                .andExpect(content().string(emptyString()));

    }


}