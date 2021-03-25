package com.codecool.barbershop.barbershop.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("Test get current user - GET /user/me")
    void getCurrentUser() throws Exception {
//        Prepare mock user
        User mockUser = new User(
                1L,
                "jon",
                "jon@gmail.com",
                "http//image.com",
                false,
                "password",
                AuthProvider.local,
                "1234-Uid-=provider");
        UserPrincipal userPrincipal = UserPrincipal.create(mockUser);


//        Prepare mocked service method
        doReturn(mockUser).when(userService).findUserById(mockUser.getId());

//        Perform GET request
        mockMvc.perform(get("/user/me").with(user(userPrincipal)))
                //                Validate 200 OK and JSON response type received
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                //                Validate response body
                .andExpect(jsonPath("name", is("jon")))
                .andExpect(jsonPath("email", is("jon@gmail.com")))
                .andExpect(jsonPath("imageUrl", is("http//image.com")))
                .andExpect(jsonPath("emailVerified", is(false)))
                .andExpect(jsonPath("provider", is("local")))
                .andExpect(jsonPath("providerId", is("1234-Uid-=provider")));

    }

    @Test
    @DisplayName("Delete current user - DELETE /user/me")
    void deleteUser() throws Exception {
        User mockUser = new User(
                1L,
                "jon",
                "jon@gmail.com",
                "http//image.com",
                false,
                "password",
                AuthProvider.local,
                "1234-Uid-=provider");
        UserPrincipal userPrincipal = UserPrincipal.create(mockUser);

        mockMvc.perform(delete("/user/me").with(user(userPrincipal)))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted"));

    }
}